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

import java.util.HashMap;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.commonsware.cwac.anddown.AndDown;
import com.commonsware.cwac.richedit.RichEditText;

public class EditorFragment extends SherlockFragment implements
    TextWatcher {
  static final int MODE_PLAIN=0;
  static final int MODE_HTML=1;
  static final int MODE_MARKDOWN=2;
  static final int MODE_RICH=3;

  private HashMap<Integer, CharSequence> contents=
      new HashMap<Integer, CharSequence>();
  private RichEditText editor=null;
  private int currentMode=MODE_PLAIN;
  private AndDown converter=new AndDown();

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    setRetainInstance(true);

    View result=inflater.inflate(R.layout.editor, container, false);

    editor=(RichEditText)result.findViewById(R.id.editor);
    editor.addTextChangedListener(this);

    return(result);
  }

  void setMode(int mode) {
    contents.put(currentMode, editor.getText());
    currentMode=mode;
    editor.setText(contents.get(currentMode));
    
    if (currentMode==MODE_RICH) {
      editor.enableActionModes(true);
    }
    else {
      editor.disableActionModes();
    }
  }

  @Override
  public void afterTextChanged(Editable s) {
    EditARamaActivity host=(EditARamaActivity)getActivity();
    CharSequence result=null;

    switch (currentMode) {
      case MODE_PLAIN:
      case MODE_RICH:
        result=s;
        break;

      case MODE_HTML:
        result=Html.fromHtml(s.toString());
        break;
        
      case MODE_MARKDOWN:
        result=Html.fromHtml(converter.markdownToHtml(s.toString()));
        break;
    }

    host.updatePreview(result);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count,
                                int after) {
    // no-op
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before,
                            int count) {
    // no-op
  }
}
