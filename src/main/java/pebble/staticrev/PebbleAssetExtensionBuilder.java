package pebble.staticrev;

import java.util.Map;

public class PebbleAssetExtensionBuilder {

    private String base;
    private String assetsHost;
    private Map revMap;

    public PebbleAssetExtensionBuilder basePath(String base) {
        this.base = base;
        return this;
    }

    public PebbleAssetExtensionBuilder revFile(String file){

        return this;
    }

    public PebbleAssetExtensionBuilder assetsHost(String assetsHost){
        this.assetsHost = assetsHost;
        return this;
    }

    public PebbleAssetExtensionBuilder enableRevFileRefresh(){

        return this;
    }

    public PebbleAssetExtensionBuilder revMap(Map map) {
        this.revMap = map;
        return this;
    }

    public PebbleAssetExtension build(){
        return new PebbleAssetExtension(new AssetConfig(this.assetsHost, this.base, this.revMap));
    }

}
