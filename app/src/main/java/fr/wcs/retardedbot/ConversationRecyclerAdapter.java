package fr.wcs.retardedbot;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class ConversationRecyclerAdapter extends RecyclerView.Adapter<ConversationRecyclerAdapter.ViewHolder> {

    private ArrayList<ChatMessage> mChatMessages = new ArrayList<>();
    private Context mContext;

    public ConversationRecyclerAdapter(ArrayList<ChatMessage> chatMessages, Context context) {
        this.mChatMessages = chatMessages;
        this.mContext = context;
    }

    @Override
    public ConversationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConversationRecyclerAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = mChatMessages.get(position);

        adaptMessageItem(holder, chatMessage);
        holder.tvTextMessage.setText(chatMessage.getTextMessage());
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDate, tvTextMessage;
        ConstraintLayout itemMessage, boxMessage;

        public ViewHolder(View v) {
            super(v);
            this.tvName = v.findViewById(R.id.author_name);
            this.tvDate = v.findViewById(R.id.message_date);
            this.tvTextMessage = v.findViewById(R.id.message_text);
            this.itemMessage = v.findViewById(R.id.item_message_constraint);
            this.boxMessage = v.findViewById(R.id.message_box);
        }
    }

    public void adaptMessageItem(ConversationRecyclerAdapter.ViewHolder holder, ChatMessage chatMessage) {

        if (chatMessage.getIdAuthor().equals(Singleton.getInstance().getUser().getIdUser())) {
            //Reverse name and date
            holder.tvName.setTextSize(10);
            holder.tvName.setText(chatMessage.getMessageDate());
            holder.tvDate.setTextSize(15);
            holder.tvDate.setText(chatMessage.getNameAuthor());

            //New background style
            Drawable drawableBackground = ContextCompat.getDrawable(mContext, R.drawable.message_box_user);
            holder.boxMessage.setBackground(drawableBackground);

            //Set position to the left instead of the right
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.itemMessage);
            constraintSet.connect(R.id.message_box,ConstraintSet.END, R.id.item_message_constraint,ConstraintSet.END,16);
            constraintSet.connect(R.id.message_box,ConstraintSet.START, R.id.vertical_guideline_left,ConstraintSet.END,0);
            constraintSet.applyTo(holder.itemMessage);

        } else {
            //Reverse name and date
            holder.tvName.setTextSize(15);
            holder.tvName.setText(chatMessage.getNameAuthor());
            holder.tvDate.setTextSize(10);
            holder.tvDate.setText(chatMessage.getMessageDate());

            //New background style
            Drawable drawableBackground = ContextCompat.getDrawable(mContext, R.drawable.message_box_bot);
            holder.boxMessage.setBackground(drawableBackground);

            //Set position to the left instead of the right
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.itemMessage);
            constraintSet.connect(R.id.message_box,ConstraintSet.END, R.id.vertical_guideline_right,ConstraintSet.START,0);
            constraintSet.connect(R.id.message_box,ConstraintSet.START, R.id.item_message_constraint,ConstraintSet.START,16);
            constraintSet.applyTo(holder.itemMessage);
        }
    }
}
