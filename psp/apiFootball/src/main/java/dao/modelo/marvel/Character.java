
package dao.modelo.marvel;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Character {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(comics, character.comics) && Objects.equals(description, character.description) && Objects.equals(events, character.events) && Objects.equals(id, character.id) && Objects.equals(modified, character.modified) && Objects.equals(name, character.name) && Objects.equals(resourceURI, character.resourceURI) && Objects.equals(series, character.series) && Objects.equals(stories, character.stories) && Objects.equals(thumbnail, character.thumbnail) && Objects.equals(urls, character.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comics, description, events, id, modified, name, resourceURI, series, stories, thumbnail, urls);
    }

    @Expose
    private Comics comics;
    @Expose
    private String description;
    @Expose
    private Events events;
    @Expose
    private String id;
    @Expose
    private String modified;
    @Expose
    private String name;
    @Expose
    private String resourceURI;
    @Expose
    private Series series;
    @Expose
    private Stories stories;
    @Expose
    private Thumbnail thumbnail;
    @Expose
    private List<Url> urls;



}
