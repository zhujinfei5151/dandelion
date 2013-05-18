/*
 * [The "BSD licence"]
 * Copyright (c) 2013 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.dandelion.core.asset.cache;

import com.github.dandelion.core.config.Configuration;

import javax.servlet.http.HttpServletRequest;

import static com.github.dandelion.core.utils.DandelionUtils.isDevModeEnabled;

public class AssetsCacheSystem {
    private static final String ASSETS_CACHE = "assets.cache";
    private static AssetsCache assetsCache;

    private AssetsCacheSystem() {
    }

    private static void initializeAssetsCache() {
        if(assetsCache == null) {
            initializeAssetsCacheIfNeeded();
        }
    }

    synchronized private static void initializeAssetsCacheIfNeeded() {
        if(assetsCache != null) return;
        String _assetsCache = Configuration.getProperty(ASSETS_CACHE);
        try {
            assetsCache = (AssetsCache) Class.forName(_assetsCache).newInstance();
        } catch (Exception e) {
            assetsCache = new DefaultAssetsCache();
        }
    }

    public static String getCacheKey(String context, String id, String resource) {
        return new StringBuilder(context).append("|").append(id).append("|").append(resource).toString();
    }

    public static String getCacheKeyFromRequest(HttpServletRequest request) {
        return getCacheKey(request.getParameter("c"), request.getParameter("id"), request.getParameter("r"));
    }

    public static boolean checkCacheKey(String cacheKey) {
        initializeAssetsCache();
        return assetsCache.checkCacheKey(cacheKey);
    }

    public static String getCacheContent(String cacheKey) {
        initializeAssetsCache();
        return assetsCache.getCacheContent(cacheKey);
    }

    public static void storeCacheContent(String context, String groupId, String location, String content) {
        initializeAssetsCache();
        assetsCache.storeCacheContent(getCacheKey(context, groupId, location), content);
    }
}