package com.example.leeyoungjae.fragmentapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.example.leeyoungjae.fragmentapp.Bean.comment;

import java.util.List;

//评论与回复列表的适配器
public class CommentAdapter extends BaseExpandableListAdapter {
    private static final String TAG="CommentAdapter";
    private List<comment> CommentList;
    private Context context;

    public CommentAdapter(Context context,List<comment>commentList){
        this.CommentList=commentList;
        this.context=context;
    }
    @Override
    public int getGroupCount() {
        return CommentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  CommentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    boolean isLike=false;
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
