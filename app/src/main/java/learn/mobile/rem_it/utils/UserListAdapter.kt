package learn.mobile.rem_it.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import learn.mobile.rem_it.R
import learn.mobile.rem_it.models.User

class UserListAdapter(private val context: Context, private val userList: List<User>) : BaseAdapter() {

    override fun getCount(): Int = this.userList.size

    override fun getItem(position: Int): Any = this.userList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: UserViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.list_item, parent, false)
            viewHolder = UserViewHolder(
                view.findViewById(R.id.txt_title),
                view.findViewById(R.id.txt_subtitle)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as UserViewHolder
        }

        val user = this.getItem(position) as User
        viewHolder.nameTextView.text = user.userName
        viewHolder.emailTextView.text = user.email

        return view
    }

    private class UserViewHolder(val nameTextView: TextView, val emailTextView: TextView)
}
