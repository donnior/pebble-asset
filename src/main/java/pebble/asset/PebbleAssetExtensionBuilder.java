package pebble.asset;

import pebble.asset.util.RefreshableRevMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PebbleAssetExtensionBuilder {

    private String base;
    private String assetsHost;
    private Map revMap;
    private String revFile;
    private boolean enableRevFileRefresh = false;

    public PebbleAssetExtensionBuilder basePath(String base) {
        this.base = base;
        return this;
    }

    public PebbleAssetExtensionBuilder revFile(String file){
        this.revFile = file;
        return this;
    }

    public PebbleAssetExtensionBuilder assetsHost(String assetsHost){
        this.assetsHost = assetsHost;
        return this;
    }

    public PebbleAssetExtensionBuilder enableRevFileRefresh(){
        this.enableRevFileRefresh = true;
        return this;
    }

    public PebbleAssetExtensionBuilder revMap(Map map) {
        this.revMap = (map != null) ? map : new HashMap();
        return this;
    }

    public PebbleAssetExtension build(){
        if (this.enableRevFileRefresh) {
            if (this.revFile == null) {
                throw new RuntimeException("Can't enable asset rev refresh because the rev file is not provided");
            }
            File file = new File(this.revFile);

            if (file.exists() && file.isFile()) {
                Map refreshableRevMap = new RefreshableRevMap(new File(this.revFile));
                return new PebbleAssetExtension(new AssetConfig(this.assetsHost, this.base, refreshableRevMap));
            }

            throw new RuntimeException("Asset rev file is invalid");
        }
        return new PebbleAssetExtension(new AssetConfig(this.assetsHost, this.base, this.revMap));
    }

}
