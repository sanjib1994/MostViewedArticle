package com.example.mostviewarticle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mostviewarticle.R;
import com.example.mostviewarticle.activity.ArticleDetailActivity;
import com.example.mostviewarticle.model.ArticleData;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    Context context;
    ArrayList<ArticleData> articleList;


    public ArticleListAdapter(Context context, ArrayList<ArticleData> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ArticleListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListAdapter.ViewHolder holder, final int position) {

        try {

            holder.textTile.setText(articleList.get(position).getTitle());
            holder.textAuthor.setText(articleList.get(position).getByline());
            holder.textDate.setText(articleList.get(position).getPublished_date());

            if(articleList.get(position).getMedia()!=null){
                Glide.with(context).load(articleList.get(position).getMedia().get(0)).into(holder.image);
            }else{
                Glide.with(context).load(R.drawable.photo).into(holder.image);
            }

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    if(articleList.get(position).getMedia()!=null) {
                        intent.putExtra("image", articleList.get(position).getMedia().get(2));
                    }else{
                        intent.putExtra("image", "");
                    }
                    intent.putExtra("detail",articleList.get(position).getDetail());
                    context.startActivity(intent);

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTile,textAuthor,textDate;
        ImageView image,imageRight;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTile = itemView.findViewById(R.id.title);
            textAuthor = itemView.findViewById(R.id.author);
            textDate = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            imageRight = itemView.findViewById(R.id.right_arrow);
            relativeLayout = itemView.findViewById(R.id.lin);
        }
    }
}
