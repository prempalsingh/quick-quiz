package byldathon.com.quickquiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpFirstFragment extends Fragment {


    public SignUpFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up_first, container, false);
        final EditText username = (EditText) rootView.findViewById(R.id.emailID);
        final EditText password = (EditText) rootView.findViewById(R.id.password);
        final Button signup = (Button) rootView.findViewById(R.id.sign_up);
        final EditText channel = (EditText) rootView.findViewById(R.id.channel);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();

                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(username.getText().toString());
                user.put("Channel", channel.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            ParseInstallation.getCurrentInstallation().saveInBackground();
                            ParsePush.subscribeInBackground(channel.getText().toString(), new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                }
                            });
                        } else {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return rootView;


    }


}
