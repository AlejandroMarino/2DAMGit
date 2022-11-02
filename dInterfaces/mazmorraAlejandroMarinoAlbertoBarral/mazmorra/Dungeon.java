import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.tree.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.awt.*;
import java.io.File;

public class Dungeon {
    private final JFrame frame;
    private JSplitPane splitHorizontal;
    private JSplitPane splitVertical;
    private NodeList rooms;
    private Node roomActual;
    private TextArea textAreaBottom;

    public static void main(String[] args) {
        new Dungeon();
    }

    private Dungeon() {
        this.frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cargarFrame();
    }

    private void cargarFrame() {
        setMenuBar();

        frame.setMinimumSize(new Dimension(600, 450));
        frame.setSize(new Dimension(1050, 700));
        frame.setTitle("Dungeons & Dragons");
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "Para comenzar una partida\nSeleccione en el menu de Opciones una nueva partida", "Como comenzar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setMenuBar() {
        //setting up the menuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setVisible(true);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(1600, 20));

        JMenu menu = new JMenu("Options");
        JMenuItem menuItem = new JMenuItem("Load Dungeon");
        JMenuItem menuItem2 = new JMenuItem("Start Run");

        menuItem.addActionListener(e -> cargarPantalla());
        menuItem2.addActionListener(e -> setRightPanel());

        menuBar.add(menu);
        menu.add(menuItem);
        menu.add(menuItem2);
        frame.setJMenuBar(menuBar);
    }

    private void cargarPantalla() {
        //setting up the main panel
        splitHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitHorizontal.setDividerLocation(250);
        //left and right panels
        setLeftPanel();
        frame.getContentPane().add(splitHorizontal);
        frame.validate();
        frame.repaint();
    }

    private void setLeftPanel() {
        JTree tree = null;
        File file = null;
        try {
            file = choseFile();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Dungeon");
            DefaultTreeModel model = new DefaultTreeModel(root);
            loadXMLDoc(root, file);
            tree = new JTree(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error al abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        JScrollPane left = new JScrollPane(tree);
        left.setPreferredSize(new Dimension(150, 450));
        splitHorizontal.setLeftComponent(left);
    }

    private File choseFile() {
        //elegir archivo
        File f = null;
        JFileChooser choser = new JFileChooser();
        choser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        int result = choser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            f = choser.getSelectedFile();
        }
        return f;
    }

    private void loadXMLDoc(DefaultMutableTreeNode parent, File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            String query = "/dungeon/room";
            XPath xPath = XPathFactory.newInstance().newXPath();
            rooms = (NodeList) xPath.compile(query).evaluate(doc, XPathConstants.NODESET);
            roomActual = rooms.item(0);
            for (int i = 0; i < rooms.getLength(); i++) {
                Node roomNode = rooms.item(i);
                DefaultMutableTreeNode roomTreeNode = new DefaultMutableTreeNode("Room "
                        + roomNode.getAttributes().getNamedItem("id").getNodeValue());
                Node descriptionNode = getNodeFrom(roomNode, "description");
                roomTreeNode.add(new DefaultMutableTreeNode(descriptionNode.getTextContent()));
                NodeList doors = getNodesFrom(roomNode, "door");
                for (int j = 0; j < doors.getLength(); j++) {
                    Node doorNode = doors.item(j);
                    roomTreeNode.add(new DefaultMutableTreeNode(doorNode.getAttributes().getNamedItem("name").getNodeValue()
                            + " -> " + doorNode.getAttributes().getNamedItem("dest").getNodeValue()));
                }
                parent.add(roomTreeNode);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error al parsear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private Node getNodeFrom(Node node, String query) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xExpress = xPath.compile(query);
        return (Node) xExpress.evaluate(node, XPathConstants.NODE);
    }

    private NodeList getNodesFrom(Node node, String query) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xExpress = xPath.compile(query);
        return (NodeList) xExpress.evaluate(node, XPathConstants.NODESET);
    }

    private void setRightPanel() {
        //splitVertical panel is divided into 2 parts

        splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitVertical.setDividerLocation(350);
        splitVertical.setMinimumSize(new Dimension(450, 450));

        paintBottomRightPanel();
        paintTopRightPanel();
        addTextToBottomPanel("Bienvenido al Dungeon");
        splitHorizontal.setRightComponent(splitVertical);
    }

    private void paintTopRightPanel() {
        //se actualiza cada vez que se cambia de habitacion
        JPanel top = new JPanel();
        top.setMinimumSize(new Dimension(450, 225));
        BorderLayout layoutTopRight = new BorderLayout();
        top.setLayout(layoutTopRight);
        JButton norte = new JButton("Norte");
        norte.setPreferredSize(new Dimension(50, 50));
        JButton sur = new JButton("Sur");
        sur.setPreferredSize(new Dimension(50, 50));
        JButton oeste = new JButton("Oeste");
        oeste.setPreferredSize(new Dimension(100, 100));
        JButton este = new JButton("Este");
        este.setPreferredSize(new Dimension(100, 100));

        TextArea textAreaTop = new TextArea();
        textAreaTop.setEditable(false);

        try {
            textAreaTop.setText(getNodeFrom(roomActual, "description").getTextContent());
            top.add(textAreaTop, BorderLayout.CENTER);
            for (int i = 0; i < getNodesFrom(roomActual, "door").getLength(); i++) {
                Node door = getNodesFrom(roomActual, "door").item(i);
                if (door.getAttributes().getNamedItem("name").getNodeValue().equals("Norte")) {
                    top.add(norte, BorderLayout.NORTH);
                    addListenerBoton(norte);
                } else if (door.getAttributes().getNamedItem("name").getNodeValue().equals("Sur")) {
                    top.add(sur, BorderLayout.SOUTH);
                    addListenerBoton(sur);
                } else if (door.getAttributes().getNamedItem("name").getNodeValue().equals("Este")) {
                    top.add(este, BorderLayout.EAST);
                    addListenerBoton(este);
                } else if (door.getAttributes().getNamedItem("name").getNodeValue().equals("Oeste")) {
                    top.add(oeste, BorderLayout.WEST);
                    addListenerBoton(oeste);
                }
            }
        } catch (XPathExpressionException e) {
            JOptionPane.showMessageDialog(frame, "Error al añadir los botones", "Error", JOptionPane.ERROR_MESSAGE);
        }
        splitVertical.setTopComponent(top);
    }

    private void addListenerBoton(JButton button) {
        button.addActionListener(e -> {
            try {
                NodeList doors = getNodesFrom(roomActual, "door");
                for (int i = 0; i < doors.getLength(); i++) {
                    Node door = doors.item(i);
                    if (door.getAttributes().getNamedItem("name").getNodeValue().equals(button.getText())) {
                        for (int j = 0; j < rooms.getLength(); j++) {
                            Node room = rooms.item(j);
                            if (room.getAttributes().getNamedItem("id").getNodeValue().equals(door.getAttributes().getNamedItem("dest").getNodeValue())) {
                                roomActual = room;
                                addTextToBottomPanel("Has entrado en la habitacion " + door.getAttributes().getNamedItem("dest").getNodeValue());
                            }
                        }
                    }
                }
                paintTopRightPanel();
            } catch (XPathExpressionException ex) {
                JOptionPane.showMessageDialog(frame, "Error al usar el boton", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void paintBottomRightPanel() {
        JPanel bottom = new JPanel();
        bottom.setMinimumSize(new Dimension(450, 225));
        BorderLayout layoutBottomRight = new BorderLayout();
        bottom.setLayout(layoutBottomRight);
        textAreaBottom = new TextArea();
        bottom.add(textAreaBottom, BorderLayout.CENTER);
        splitVertical.setBottomComponent(bottom);
    }

    private void addTextToBottomPanel(String text) {
        textAreaBottom.append(text + "...\n");
    }
}