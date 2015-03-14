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
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingListFragment extends Fragment {

    private RecyclerView pendingListView;
    View v;

    public PendingListFragment() {
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
        v = inflater.inflate(R.layout.fragment_pending_list, container, false);
        pendingListView = (RecyclerView)v.findViewById(R.id.pendingListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pendingListView.setLayoutManager(layoutManager);

        final ArrayList<String> completed = new ArrayList<>();
//        ParseUser.getCurrentUser().put("Pending", pending);
//        ParseUser.getCurrentUser().saveInBackground();
//        final ArrayList<String> pending2 = (ArrayList<String>)ParseUser.getCurrentUser().get("Pending");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Assignment");
        final ArrayList<ParseObject> newList = new ArrayList<>();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for(int i = 0; i < parseObjects.size(); i++){
                    if(!completed.contains(parseObjects.get(i).getString("Name"))){
                        newList.add(parseObjects.get(i));
                    }
                    Adapter adapter = new Adapter(newList);
                    pendingListView.setAdapter(adapter);
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
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_quiz_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(linearLayout);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {

            holder.name.setText(parseObjects.get(position).getString("Name"));
            holder.subject.setText(parseObjects.get(position).getString("Subject"));
            holder.time.setText(parseObjects.get(position).getString("Time"));
            holder.number.setText(parseObjects.get(position).getString("Number"));
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
