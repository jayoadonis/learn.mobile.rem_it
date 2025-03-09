package learn.mobile.rem_it

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import learn.mobile.rem_it.models.ActivityItem

class MyExpandableListAdapter(
    private val context: Context,
    private val listDataHeader: List<String>,
    private val listDataChild: HashMap<String, List<ActivityItem>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = listDataHeader.size

    override fun getChildrenCount(groupPosition: Int): Int {
        return listDataChild[listDataHeader[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any = listDataHeader[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listDataChild[listDataHeader[groupPosition]]?.get(childPosition)
            ?: ActivityItem("", "", Context::class.java)
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val headerTitle = getGroup(groupPosition) as String
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.learn__mobile__rem_it__expandable_list_group, parent, false)
        val lblListHeader = view.findViewById<TextView>(R.id.lblListHeader)
        val indicator = view.findViewById<ImageView>(R.id.indicator)

        lblListHeader.text = headerTitle
        // Toggle the indicator icon based on expansion state
        if (isExpanded) {
            indicator.setImageResource(R.drawable.ic_expand_less)
        } else {
            indicator.setImageResource(R.drawable.ic_expand_more)
        }
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val activityItem = getChild(groupPosition, childPosition) as ActivityItem
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.learn__mobile__rem_it__expandable_list_item, parent, false)
        val lblListItem = view.findViewById<TextView>(R.id.lblListItem)
        val txtDescription = view.findViewById<TextView>(R.id.txtDescription)

        lblListItem.text = activityItem.name
        txtDescription.text = activityItem.description

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}
