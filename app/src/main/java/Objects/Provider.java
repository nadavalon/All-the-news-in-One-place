package Objects;

import controllers.DataProvider;

/**
 * Created by Nadav Alon on 04/02/2016.
 */
public class Provider {

    private DataProvider.Providers providerType;
    private String url;
    private String name;
    private int image;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Provider(String url, String name, int image, DataProvider.Providers providerType) {
        this.url = url;
        this.name = name;
        this.image = image;
        this.providerType = providerType;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public DataProvider.Providers getProviderType() {
        return providerType;
    }
}
