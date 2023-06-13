package dao.retrofit;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import okhttp3.Cookie;
import okhttp3.Cookie.Builder;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import retrofit2.internal.EverythingIsNonNull;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.*;
import java.util.Map.Entry;

public final class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    @EverythingIsNonNull
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (this.cookieHandler != null) {
            List<String> cookieStrings = new ArrayList<>();

            for (Cookie cookie : cookies) {
                cookieStrings.add(cookie.toString());
            }

            Map<String, List<String>> multimap = Collections.singletonMap(Constants.SET_COOKIE, cookieStrings);

            try {
                this.cookieHandler.put(url.uri(), multimap);
            } catch (IOException var6) {
                Platform.get().log(5, Constants.MSG_FAIL_SAVING + url.resolve(Constants.LINK_FAIL), var6);
            }
        }
    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        Map<String, List<String>> headers = Collections.emptyMap();

        Map<String, List<String>> cookieHeaders;
        try {
            cookieHeaders = this.cookieHandler.get(url.uri(), headers);
        } catch (IOException var10) {
            Platform.get().log(5, Constants.MSG_FAIL_LOADING + url.resolve(Constants.LINK_FAIL), var10);
            return Collections.emptyList();
        }

        List<Cookie> cookies = null;
        Iterator<Entry<String, List<String>>> var5 = cookieHeaders.entrySet().iterator();

        while (true) {
            Entry<String, List<String>> entry;
            String key;
            do {
                do {
                    if (!var5.hasNext()) {
                        return cookies != null ? Collections.unmodifiableList(cookies) : Collections.emptyList();
                    }

                    entry = var5.next();
                    key = entry.getKey();
                } while (!Constants.COOKIE.equalsIgnoreCase(key) && !Constants.COOKIE_2.equalsIgnoreCase(key));
            } while ((entry.getValue()).isEmpty());

            String header;
            for (Iterator<String> var8 = (entry.getValue()).iterator(); var8.hasNext();
                 cookies.addAll(this.decodeHeaderAsJavaNetCookies(url, header))) {
                header = var8.next();
                if (cookies == null) {
                    cookies = new ArrayList<>();
                }
            }
        }
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList<>();
        int pos = 0;
        int pairEnd;
        for (int limit = header.length(); pos < limit; pos = pairEnd + 1) {
            pairEnd = Util.delimiterOffset(header, pos, limit, Constants.DELIMITERS);
            int equalsSign = Util.delimiterOffset(header, pos, pairEnd, Constants.DELIMITER_EQUALS);
            String name = Util.trimSubstring(header, pos, equalsSign);
            if (!name.startsWith(Constants.DOLLAR_SYMBOL)) {
                String value = equalsSign < pairEnd ? Util.trimSubstring(header, equalsSign + 1, pairEnd) : Constants.EMPTY;
                if (value.startsWith(Constants.SLASH) && value.endsWith(Constants.SLASH)) {
                    value = value.substring(1, value.length() - 1);
                }
                result.add((new Builder()).name(name).value(value).domain(url.host()).build());
            }
        }
        return result;
    }
}
