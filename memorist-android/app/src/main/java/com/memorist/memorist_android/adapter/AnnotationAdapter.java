package com.memorist.memorist_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.memorist.memorist_android.R;
import com.memorist.memorist_android.model.Annotation;
import com.memorist.memorist_android.model.Memory;
import com.memorist.memorist_android.model.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnnotationAdapter extends ArrayAdapter<Annotation> {

    // The container list of all existing meeting data objects.
    private ArrayList<Annotation> dataSet;

    // The context that the adapter will work on.
    private Context context;

    private Memory memory;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.annotation_textpart) TextView tvTextPart;
        @BindView(R.id.annotation_valuepart) TextView tvValuePart;
        @BindView(R.id.annotation_owner) TextView tvOwner;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AnnotationAdapter(ArrayList<Annotation> dataSet, Memory memory, Context context) {
        super(context, R.layout.adapter_annotation_row, dataSet);

        this.dataSet = dataSet;
        this.context = context;
        this.memory = memory;
    }

    /**
     * Constructs the view and sets it content. Returns the result which will be displayed.
     * It's a get method and it will be called by the occupant listView object.
     *
     * @param position: The placement of the specific row within the list.
     * @param convertView: The view object to display
     * @param parent: The parent object which holds views jointly inside.
     * @return The view object to display as filled with content.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // The comment in the placement of the specific row.
        final Annotation annotation = getItem(position);

        /*
         * ViewHolder is used to avoid instantiating a view for every item in your adapter,
         * when a view scrolls off-screen, it can be reused, or recycled.
         */
        ViewHolder viewHolder;

        /*
         * If convertView view object has never been constructed, then inflate the layout,
         * create the viewHolder and set convertView's tag.
         * Else get convertView's tag.
         */
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_annotation_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Sets the content of the view.
        setViewContent(viewHolder, annotation);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder, the holder of view which will be displayed.
     * @param annotation: The meeting object which holds the content.
     */
    private void setViewContent(ViewHolder viewHolder, final Annotation annotation) {
        if(annotation != null) {
            String targetStart = annotation.getTarget()[0].getSelector()[0].getStart();
            String targetEnd = annotation.getTarget()[0].getSelector()[0].getEnd();
            String targetValue = annotation.getBody()[0].getValue();
            String owner = "@" + annotation.getCreator()[0].getNickname() + " annotated: ";

            StringBuilder storyBuilder = new StringBuilder();
            for(Text text: memory.getTexts()) {
                storyBuilder.append(text.getText());
            }

            String text = storyBuilder.toString();
            String textPart = "\"" + text.substring(Integer.valueOf(targetStart), Integer.valueOf(targetEnd)) + "\"";

            viewHolder.tvTextPart.setText(textPart);
            viewHolder.tvValuePart.setTextColor(Color.BLUE);
            viewHolder.tvValuePart.setText(targetValue);
            viewHolder.tvValuePart.setTextColor(Color.BLACK);
            viewHolder.tvOwner.setText(owner);
            viewHolder.tvOwner.setTextColor(Color.BLACK);
            viewHolder.tvOwner.setPaintFlags(viewHolder.tvOwner.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        }
    }
}