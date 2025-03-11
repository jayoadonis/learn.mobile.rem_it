package learn.mobile.rem_it

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import learn.mobile.rem_it.midterm.activity_i.MapsActivity
import learn.mobile.rem_it.midterm.activity_i.MapsIActivity
import learn.mobile.rem_it.midterm.activity_ii.MainNasaImageActivity
import learn.mobile.rem_it.midterm.activity_ii.MainWeatherActivity
import learn.mobile.rem_it.models.ActivityItem

class AccordionActivity : AppCompatActivity() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: MyExpandableListAdapter
    private lateinit var listDataHeader: List<String>
    private lateinit var listDataChild: HashMap<String, List<ActivityItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.learn__mobile__rem_it__activity_accordion)

        expandableListView = findViewById(R.id.expandableListView)

        //REM: Prepare list data
        prepareListData()

        adapter = MyExpandableListAdapter(this, listDataHeader, listDataChild)
        expandableListView.setAdapter(adapter)

        //REM: Handle child item click: Launch the corresponding activity
        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val group = listDataHeader[groupPosition]
            val activityItem = listDataChild[group]?.get(childPosition)
            activityItem?.let {
                //REM: Launch the activity using the stored class
                val targetActivity = Intent(this, it.activityClass)
                targetActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                super@AccordionActivity.startActivity(targetActivity)
            }
            true
        }
    }

    private fun prepareListData() {

        listDataChild = LinkedHashMap<String, List<ActivityItem>>()

        //REM: Replace DummyActivity::class.java with your actual activity classes
        listDataChild["PRELIM"] = listOf(
            ActivityItem("activity_001", "Image Prototype with Figma", PrototypeWithFigmaActivity::class.java),
            ActivityItem("activity_002", "Sign In and Sign Up", MainActivity::class.java),
            ActivityItem("activity_003", "Dashboard and list registered accounts with Local DB (SQLiteOpenHelper)", DashboardActivity::class.java),
            ActivityItem("activity_004", "Dashboard and Profile with Local DB (SQLiteOpenHelper)", DashboardActivityI::class.java),
            ActivityItem("Examination", "Simple Calculator", SimpleCalcActivity::class.java)
        )

        listDataChild["MIDTERM"] = listOf(
            ActivityItem("activity_001", "Google Map (Free Subscription API)", MapsActivity::class.java),
            ActivityItem("activity_001a", "Google Map (Free Subscription API)", MainNasaImageActivity::class.java),
        )

        listDataChild["FINAL"] = listOf(
            ActivityItem("...", "...", SimpleCalcActivity::class.java),
        )

        listDataHeader = listDataChild.keys.toList();
    }
}

