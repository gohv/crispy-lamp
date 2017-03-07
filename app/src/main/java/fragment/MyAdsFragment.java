package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import model.Ad;
import xyz.georgihristov.myadds.R;
import controller.RealmRecyclerAdapter;
import controller.SerializeToJson;

/**
 * Created by gohv on 02.03.17.
 */
public class MyAdsFragment extends Fragment {
    private Realm realm;
    private Button saveButton;
    private LinearLayoutManager layoutManager;
    private RealmRecyclerAdapter adapter;
    private String json;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View view =
                inflater.inflate(R.layout.fragment_list,
                        container,false);
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.listRecyclerView);
        realm = Realm.getDefaultInstance();
        adapter = new RealmRecyclerAdapter(this,realm.where(Ad.class).findAllAsync(),getContext());
        saveButton = (Button)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Serializing", Toast.LENGTH_SHORT).show();
                try {
                    json = new SerializeToJson().serialize(realm);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), json, Toast.LENGTH_SHORT).show();

            }

        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }

    public void clearList() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Ad.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
    }
}
