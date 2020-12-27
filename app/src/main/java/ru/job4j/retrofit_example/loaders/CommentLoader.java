package ru.job4j.retrofit_example.loaders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ru.job4j.retrofit_example.R;
import ru.job4j.retrofit_example.model.Comment;

public class CommentLoader {
    private static class ResultHolder extends RecyclerView.ViewHolder {
        private View view;
        ResultHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }
    public static class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {
        private List<Comment> items;
        public ResultAdapter(List<Comment> list) {
            this.items = list;
        }
        @NonNull
        @Override
        public ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.result_module, viewGroup, false);
            return new ResultHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ResultHolder holder, int i) {
            Comment c = items.get(i);
            TextView text = holder.view.findViewById(R.id.module_textView);
            String content = "ID: " + c.getId() + "\nText: " + c.getText() + "\n";
            text.setText(content);
        }
        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
