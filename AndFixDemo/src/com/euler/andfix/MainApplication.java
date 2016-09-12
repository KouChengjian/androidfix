/*
 * 
 * Copyright (c) 2015, alipay.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.euler.andfix;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * sample application
 * 
 * @author sanping.li@alipay.com
 * 
 */
public class MainApplication extends Application {
	private static final String TAG = "Application";

	private static final String APATCH_PATH = "/out.apatch";
	private static final String DIR = "apatch";//补丁文件夹
	
	/**
	 * patch manager
	 */
	private PatchManager mPatchManager;

	@Override
	public void onCreate() {
		super.onCreate();
		// initialize
		mPatchManager = new PatchManager(this);
		mPatchManager.init("1.0.1");
		Log.d(TAG, "inited.");

		// load patch
		mPatchManager.loadPatch();
		Log.d(TAG, "apatch loaded.");

		// add patch at runtime
		try {
			// .apatch file path
			String patchFileString = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
			mPatchManager.addPatch(patchFileString);
			Log.e("path", "apatch:" + patchFileString);
			
			File f = new File(this.getFilesDir(), DIR + APATCH_PATH);
            if (f.exists()) {
                boolean result = new File(patchFileString).delete();
                if (!result)
                    Log.e(TAG, patchFileString + " delete fail");
            }
			
		} catch (IOException e) {
			Log.e("IOException", "", e);
		}

	}
}
