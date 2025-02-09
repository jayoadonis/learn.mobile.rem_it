package learn.mobile.rem_it.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import learn.mobile.rem_it.R
import learn.mobile.rem_it.models.User

class UserRecycleViewAdapter(private val users: List<User>, private val onItemClick: (User) -> Unit) : RecyclerView.Adapter<UserRecycleViewAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.txt_title)
        val emailTextView: TextView = view.findViewById(R.id.txt_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = this.users[position]
        holder.nameTextView.text = user.userName
        holder.emailTextView.text = user.email

        holder.itemView.setOnClickListener { this.onItemClick(user) }
    }

    override fun getItemCount(): Int = this.users.size
}
