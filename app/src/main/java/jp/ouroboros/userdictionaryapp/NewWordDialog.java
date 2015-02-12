package jp.ouroboros.userdictionaryapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordDialog extends DialogFragment {
    static private final String TAG = NewWordDialog.class.getSimpleName();

    private final NewWordDialog self = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.clear_dialog_fragment_theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Dialog dialog = getDialog();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.new_word, container);
        dialog.getWindow().getAttributes().windowAnimations = R.style.anim_style_notification;
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final EditText editWord = (EditText) view.findViewById(R.id.edit_word);
        final EditText shorcutWord = (EditText) view.findViewById(R.id.edit_shortcut);
        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editWord.getText().toString();
                String shortcut = shorcutWord.getText().toString();
                if (word.isEmpty()) {
                    Toast.makeText(v.getContext(), "Word is Empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                save(word, shortcut);
            }
        });
        return view;
    }

    private void save(String word, String shortcut) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDictionary.Words.WORD, word);
        contentValues.put(UserDictionary.Words.SHORTCUT, shortcut);
        getActivity().getContentResolver().insert(UserDictionary.Words.CONTENT_URI, contentValues);
        dismiss();
    }

}
