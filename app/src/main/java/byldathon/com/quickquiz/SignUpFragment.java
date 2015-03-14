package byldathon.com.quickquiz;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser pUser = ParseUser.getCurrentUser();
        if ((pUser != null)
                && (pUser.isAuthenticated())
                && (pUser.getSessionToken() != null)) {
            Log.d(TAG, pUser.getUsername() + pUser.getSessionToken());
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);

            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final EditText username = (EditText) rootView.findViewById(R.id.emailID);
        final EditText password = (EditText) rootView.findViewById(R.id.password);
        final TextView signup = (TextView) rootView.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                SignUpFirstFragment newFragment = new SignUpFirstFragment();
                transaction.replace(R.id.signup_container, newFragment).addToBackStack("SignIn").commit();
            }
        });
        Button signIn = (Button) rootView.findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(
                        username.getText().toString(),
                        password.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if (parseUser != null) {
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Login failed")
                                            .setCancelable(true)
                                            .setMessage("Logging in to QuickQuiz failed !")
                                            .show();
                                }
                            }
                        }
                );
            }
        });
        return rootView;
    }


}
