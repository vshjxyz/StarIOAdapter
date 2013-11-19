package com.uforge.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class StarIOAdapter extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("check")) {
            this.check(callbackContext);
            return true;
        }
        return false;
    }

    private void check(CallbackContext callbackContext) {
        if (true) {
            callbackContext.success(true);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}