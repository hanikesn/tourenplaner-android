package de.uni.stuttgart.informatik.ToureNPlaner.UI.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.uni.stuttgart.informatik.ToureNPlaner.Data.Node;
import de.uni.stuttgart.informatik.ToureNPlaner.R;
import de.uni.stuttgart.informatik.ToureNPlaner.UI.Overlays.NodeDrawable;

import java.util.ArrayList;

public class NodeListAdapter extends ArrayAdapter<Node> {
	private ArrayList<Node> nodeList;
	private final boolean sourceIsTarget;

	public NodeListAdapter(ArrayList<Node> nodeList, Context context, boolean sourceIsTarget) {
		super(context, R.layout.list_item, nodeList);
		this.nodeList = nodeList;
		this.sourceIsTarget = sourceIsTarget;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LinearLayout itemLayout;

		Node node = getItem(position);

		itemLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.nodelistadapteritem, parent, false);

		TextView tvUser = (TextView) itemLayout.findViewById(R.id.toptext);
		tvUser.setText(node.getName());

		TextView tvText = (TextView) itemLayout.findViewById(R.id.bottomtext);
		tvText.setText(node.getGeoPoint().toString());

		ImageView ImageView = (ImageView) itemLayout.findViewById(R.id.nodelisticon);
		Drawable icon;

		// sets the icon depending on the index
		if (!sourceIsTarget && position == 0) {
			icon = new NodeDrawable(NodeDrawable.MarkerType.START);
		} else if (!sourceIsTarget && position == getCount() - 1) {
			icon = new NodeDrawable(NodeDrawable.MarkerType.END);
		} else if (sourceIsTarget) {
			icon = new NodeDrawable(NodeDrawable.MarkerType.START);
		} else {
			icon = new NodeDrawable(NodeDrawable.MarkerType.MIDDLE);
		}

		((NodeDrawable) icon).setLabel(node.getShortName());

		ImageView.setImageDrawable(icon);
		return itemLayout;
	}


	public int getCount() {
		return nodeList.size();
	}


	public Node getItem(int position) {
		return nodeList.get(position);
	}


	public long getItemId(int position) {
		return position;
	}

	public void onRemove(int index) {
		if (index < 0 || index > nodeList.size()) return;
		nodeList.remove(index);
	}

	public void onDrop(int from, int to) {
		Node temp = nodeList.get(from);
		nodeList.remove(from);
		nodeList.add(to, temp);
	}
}
