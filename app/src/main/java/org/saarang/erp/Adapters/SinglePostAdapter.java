package org.saarang.erp.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Activities.SinglePostActivity;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Helpers.TimeHelper;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seetharaman on 04-07-2015.
 */
public class SinglePostAdapter extends RecyclerView.Adapter<SinglePostAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Integer> viewTypes;
    ERPPost mPost;
    TimeHelper th=new TimeHelper();
    List<ERPComment> mItems;
    EditText etComment;

    public SinglePostAdapter(Context context, ERPPost post, List<ERPComment> items){
        mContext = context;
        viewTypes = new ArrayList<>(2);
        mPost = post;
        mItems = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvTitle, tvInfo, tvWall, tvPostDate;
        ImageView ivProfilePic;
        Button bComment, bAcknowledge;
        View mView;
        ImageView ivSend;


        ImageView ivCommentsProfilePic;
        TextView tvProfileName, tvComment, tvTime;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            tvWall = (TextView) view.findViewById(R.id.tvWall);
            ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
            bComment = (Button) view.findViewById(R.id.bComments);
            bAcknowledge = (Button) view.findViewById(R.id.bAcknowledge);
            mView = view.findViewById(android.R.id.content);
            tvPostDate= (TextView)view.findViewById(R.id.tvPostDate);
            ivSend = (ImageView) view.findViewById(R.id.ivSend);


            ivCommentsProfilePic =(ImageView)itemView.findViewById(R.id.ivComment);
            tvProfileName =(TextView)itemView.findViewById(R.id.tvComment1);
            tvComment =(TextView)itemView.findViewById(R.id.tvComment2);
            tvTime =(TextView)itemView.findViewById(R.id.tvComment3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_single_post, parent, false);
                etComment = (EditText) view.findViewById(R.id.etCommentNew);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(position == 0)
        {

            //give post details here
            holder.tvName.setText(mPost.getPostedBy().getName());
            holder.tvPostDate.setText(th.getRelative(mPost.getCreatedOn()));
            holder.tvTitle.setText(mPost.getTitle());

            holder.tvInfo.setText(mPost.getInfo());
            holder.tvWall.setText(mPost.getWall().getName());
//            holder.bComment.setText("Comment");

            final String profilePicUrl = URLConstants.SERVER + "api/users/" + mPost.getPostedBy().get_id() + "/profilePic";
            Glide.with(mContext)
                    .load(profilePicUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_people)
//                    .crossFade()
                    .into(holder.ivProfilePic);

            /**
             * Alert dialog with contact details
             */
            holder.ivProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater li = (LayoutInflater) mContext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View dialoglayout = li.inflate(R.layout.alert_profile_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(dialoglayout);
                    ImageView imageView = (ImageView) dialoglayout.findViewById(R.id.imageView);
                    TextView tvName = (TextView) dialoglayout.findViewById(R.id.tvName);
                    tvName.setText(mPost.getPostedBy().getName());
                    Glide.with(mContext)
                            .load(profilePicUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_people)
                            .crossFade()
                            .into(imageView);

                    builder.show();
                }
            });


            holder.ivSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Connectivity.isConnected()) {
                                String comment = etComment.getText().toString();
                                if (comment.isEmpty()){
                                    UIUtils.showSnackBar(view, "Please add some text in the comments field before sending");
                                    return;
                                }
                                final SinglePostActivity.AddComment addComment = new SinglePostActivity.AddComment();
                                addComment.execute(comment);
                            } else {
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                            }
                            etComment.setText("");
                        }
                    });
//                }
//            });

            if (mPost.isAcknowledged()){
                markAsAcknowledged(holder.bAcknowledge);
            }

            holder.bAcknowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Connectivity.isConnected()){
                        new AcknowledgePost().execute( mPost.getPostId(), mPost.getAcknowledge() );
                        markAsAcknowledged(holder.bAcknowledge);
                    }
                    else{
                        UIUtils.showSnackBar(holder.tvName,
                                mContext.getResources().getString(R.string.error_connection));
                    }
                }
            });
        }
        else
        {
            //give comments here
            holder.tvComment.setText(mItems.get(position-1).getInfo());
            holder.tvProfileName.setText(mItems.get(position-1).getCreatedBy().getName());
            holder.tvTime.setText(th.getRelative(mItems.get(position-1).getCreatedOn()));
        }
    }



    @Override
    public int getItemCount() {
        return (mItems.size()+1);
    }

    private void markAsAcknowledged(Button bAcknowledge) {
        bAcknowledge.setEnabled(false);
        bAcknowledge.setText("Acknowledged");
        bAcknowledge.setTextColor(mContext.getResources().getColor(R.color.indigo_color_disabled));
    }

    private class AcknowledgePost extends AsyncTask<String, Void, Boolean> {

        JSONObject jsonObject;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                ArrayList<PostParam> param = new ArrayList<>();
                param.add(new PostParam("a", "Asd"));
                jsonObject = PostRequest.execute(URLConstants.URL_POST_ACKNOWLEDGE + params[0], param, ERPProfile.getERPUserToken(mContext));
                if (jsonObject.getInt("status") == 200){
                    DatabaseHelper data = new DatabaseHelper(mContext);
                    JSONArray jsonArray = new JSONArray(params[1]);
                    jsonArray.put(new JSONObject("{" +
                            "\"_id\":\""+ ERPProfile.getERPUserId(mContext) +  "\"," +
                            "\"name\": \"" + ERPProfile.getERPUserName(mContext) +"\"" +
                            "}"));
                    data.markPostAsUpdated(params[0], jsonArray.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

}
