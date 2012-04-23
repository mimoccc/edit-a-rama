/***
  Copyright (c) 2012 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/    

package com.commonsware.android.editarama;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class EditARamaActivity extends SherlockFragmentActivity
    implements ActionBar.TabListener {
  private EditorFragment editor=null;
  private PreviewFragment preview=null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
                                 .add(R.id.editorFrame,
                                      new EditorFragment())
                                 .add(R.id.previewFrame,
                                      new PreviewFragment()).commit();

    }

    ActionBar bar=getSupportActionBar();

    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    bar.addTab(bar.newTab().setText(R.string.plain_text)
                  .setTabListener(this));
    bar.addTab(bar.newTab().setText(R.string.html).setTabListener(this));
    bar.addTab(bar.newTab().setText(R.string.markdown)
                  .setTabListener(this));
    bar.addTab(bar.newTab().setText(R.string.richtext)
                  .setTabListener(this));
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    if (editor == null) {
      editor=
          (EditorFragment)getSupportFragmentManager().findFragmentById(R.id.editorFrame);
    }

    if (editor!=null) {
      editor.setMode(tab.getPosition());
    }
  }

  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    // no-op
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
    // no-op
  }
  
  void updatePreview(CharSequence prose) {
    if (preview == null) {
      preview=
          (PreviewFragment)getSupportFragmentManager().findFragmentById(R.id.previewFrame);
    }

    preview.update(prose);
  }
}