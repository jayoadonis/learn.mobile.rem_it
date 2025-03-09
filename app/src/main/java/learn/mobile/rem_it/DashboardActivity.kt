package learn.mobile.rem_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import learn.mobile.rem_it.databinding.ActivityDashboardBinding;
import learn.mobile.rem_it.models.User
import learn.mobile.rem_it.models.UserDataAccessObject
import learn.mobile.rem_it.utils.SessionManager
import learn.mobile.rem_it.utils.UserListAdapter
import learn.mobile.rem_it.utils.UserRecycleViewAdapter

class DashboardActivity : AppCompatActivity() {

    private lateinit var activityDashboardBind: ActivityDashboardBinding;
    private lateinit var sessionManager: SessionManager;

    private lateinit var userDAO: UserDataAccessObject;

    //REM: [TODO] .|. Refactor later...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.userDAO = UserDataAccessObject(this);
        this.activityDashboardBind = ActivityDashboardBinding.inflate(super.getLayoutInflater());

        super.setContentView(this.activityDashboardBind.root);
//        setContentView(R.layout.activity_dashboard)

        this.sessionManager = SessionManager(this)

        if( !this.sessionManager.isLoggedIn() ) {
            //REM: [TODO] .|. Refactor later...
            Toast.makeText(this, "No Active Session found, please register or/and sign in first", Toast.LENGTH_LONG).show();
            this.sessionManager.clearSession();
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }

        val lblDashboard = this.activityDashboardBind.lblDashboardTitle.text.toString();
//        val listViewTodo = this.activityDashboardBind.listViewTodo;
        val recyclerView = this.activityDashboardBind.recycleViewTodos;

        this.activityDashboardBind.lblDashboardTitle.text = lblDashboard.plus( ": [${this.sessionManager.getUserName()}]")

//        listViewTodo.adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_1,
//            this.userDAO.getAllUsers()
//        );

//        listViewTodo.adapter = UserListAdapter(this, this.userDAO.getAllUsers() );

//        listViewTodo.setOnClickListener { parent, view, position, id ->
//            val clickedItem = parent.getItemAtPosition(position) as User
//
//            Toast.makeText(this, "Clicked: $clickedItem with $id", Toast.LENGTH_SHORT).show()
//        }

        recyclerView.layoutManager = LinearLayoutManager(this);

        recyclerView.adapter = UserRecycleViewAdapter( this.userDAO.getAllUsers() ) {
            Toast.makeText(this, "Clicked: ${it.userName} with ${it.userID}", Toast.LENGTH_SHORT).show()
        };

        this.activityDashboardBind.btnDashboardLogOut.setOnClickListener{
            //REM: [TODO] .|. Refactor later...
            this.sessionManager.clearSession();
            super.startActivity( Intent(this, MainActivity::class.java) );
            super.finish();
        }
    }
}