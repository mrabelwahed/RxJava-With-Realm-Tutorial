package com.ramadan_apps.rxjavawithrealmcache;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mahmoud Ramadan on 12/22/17.
 */

public class ReposAdapter  extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Repo>repos;

    public  ReposAdapter(Context context , List<Repo>repos){
        this.context = context;
        this.repos = repos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ReposAdapter.RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.repo_item,parent,false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.RepoViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.setItemContent(repo);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{

        TextView tvRepoName,tvRepoId;

        public RepoViewHolder(View itemView) {
            super(itemView);
            tvRepoName = (TextView) itemView.findViewById(R.id.repo_name);
            tvRepoId = (TextView) itemView.findViewById(R.id.repo_id);
        }

        void setItemContent(Repo repo){
            tvRepoName.setText(repo.getName());
            tvRepoId.setText(repo.getId());
        }
    }
}
