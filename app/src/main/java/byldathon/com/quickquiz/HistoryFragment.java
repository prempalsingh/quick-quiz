package byldathon.com.quickquiz;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {


    private RecyclerView historyView;
    private ProgressBar progressBar;
    View v;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history, container, false);
        historyView = (RecyclerView)v.findViewById(R.id.historyView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        historyView.setLayoutManager(layoutManager);

        final ArrayList<String> completed = new ArrayList<>();
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Assignment");
        final ArrayList<ParseObject> newList = new ArrayList<>();
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                progressBar.setVisibility(View.GONE);
                for(int i = 0; i < parseObjects.size(); i++){
                    if(completed.contains(parseObjects.get(i).getString("Name"))){
                        if(parseObjects.get(i).getString("Channel").equals(ParseUser.getCurrentUser().getString("Channel"))) {
                            newList.add(parseObjects.get(i));
                        }
                    }
                    Adapter adapter = new Adapter(newList);
                    historyView.setAdapter(adapter);
                }
            }
        });

        return v;
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements View.OnClickListener{

        ArrayList<ParseObject> parseObjects;

        public Adapter(ArrayList<ParseObject> parseObjects){
            this.parseObjects = parseObjects;
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(linearLayout);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {

            holder.name.setText(parseObjects.get(position).getString("Name"));
            holder.subject.setText(parseObjects.get(position).getString("Subject"));
        }

        @Override
        public int getItemCount() {
            return parseObjects.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView subject;
            TextView time;
            TextView number;
            public ViewHolder(View itemView) {
                super(itemView);
                this.name = (TextView) itemView.findViewById(R.id.name);
                this.subject = (TextView) itemView.findViewById(R.id.subject);
                this.time = (TextView) itemView.findViewById(R.id.time);
                this.number = (TextView) itemView.findViewById(R.id.questions);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

}
