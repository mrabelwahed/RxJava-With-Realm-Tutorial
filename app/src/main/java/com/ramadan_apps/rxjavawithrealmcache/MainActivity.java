package com.ramadan_apps.rxjavawithrealmcache;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
 private RecyclerView rvRepos;
  private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        realm = Realm.getDefaultInstance();
        GitHubReposApi api = ApiClient.getInstance()
                .getGithubReposApi();

           Observable<List<Repo>> dbObservable = Observable.create(e -> getDBRepos());

        if (isNetworkAvailable()){
            api.getUserRepos("mrabelwahed")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(repos ->{
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(trRealm->trRealm.copyToRealm(repos));
                        Log.d("ooo",realm.where(Repo.class).findAll().size()+"");
                        return repos;
                    } )
                    .mergeWith(dbObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->setAdapterData(response));
        }else{
            setAdapterData(getDBRepos());

        }

    }


    private List<Repo> getDBRepos(){
       return realm.copyFromRealm(realm.where(Repo.class).findAll());
    }

    private void initUI(){
        rvRepos = (RecyclerView) findViewById(R.id.recycler_view);
    }

     void setAdapterData(List<Repo> repos){
         ReposAdapter adapter = new ReposAdapter(getApplicationContext(),repos);
         rvRepos.setLayoutManager(new LinearLayoutManager(this));
         rvRepos.setItemAnimator(new DefaultItemAnimator());
         rvRepos.setAdapter(adapter);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  info!=null && info.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
