package com.example.ordermain_1.PageComOrderPage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ordermain_1.DataBaseObject.DBobject
import com.example.ordermain_1.MainActivityViewModel
import com.example.ordermain_1.PageGoOrderPage.DrinkmenuDatabase.DrinkmenuDataBase
import com.example.ordermain_1.PageGoOrderPage.DrinkmenuDatabase.DrinkmenuEntity
import com.example.ordermain_1.PageGoOrderPage.GoOrderPage
import com.example.ordermain_1.PageGoOrderPage.RealmenuDatabase.OnDeleteListener
import com.example.ordermain_1.PageGoOrderPage.RealmenuDatabase.RealmenuDataBase
import com.example.ordermain_1.PageGoOrderPage.RealmenuDatabase.RealmenuEntity
import com.example.ordermain_1.PageGoOrderPage.SidemenuDatabase.SidemenuDataBase
import com.example.ordermain_1.PageGoOrderPage.SidemenuDatabase.SidemenuEntity
import com.example.ordermain_1.PageLastYeah.LastYeah
import com.example.ordermain_1.PageLastYeah.LastYeahDatabase.LastDataBase
import com.example.ordermain_1.PageLastYeah.LastYeahDatabase.LastEntity
import com.example.ordermain_1.PageMenuPageUI.fakeComOrder
import com.example.ordermain_1.R
import kotlinx.android.synthetic.main.activity_completed_order_page.*
import kotlinx.android.synthetic.main.activity_menu_page_ui.*


@SuppressLint("StaticFieldLeak")
class Completed_Order_Page : AppCompatActivity(), OnDeleteListener {

    val TAG: String ="로그"

    lateinit var realmenuList : List<RealmenuEntity>
    lateinit var sidemenuList : List<SidemenuEntity>
    lateinit var drinkmenuList : List<DrinkmenuEntity>
    lateinit var lastList : List<LastEntity>

    lateinit var realdb : RealmenuDataBase
    lateinit var sidedb : SidemenuDataBase
    lateinit var drinkdb : DrinkmenuDataBase
    lateinit var lastdb : LastDataBase

    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_order_page)

        realdb = RealmenuDataBase.getinstance(this)!!
        sidedb = SidemenuDataBase.getinstance(this)!!
        drinkdb = DrinkmenuDataBase.getinstance(this)!!
        lastdb = LastDataBase.getinstance(this)!!


        wowOrderGobtn.setOnClickListener {
//            startActivity(Intent(this,LastYeah::class.java))
        }

        realmenugetAll()
        sidemenugetAll()
        drinkmenugetAll()


    }
    private fun drinkmenugetAll(){
        var dirnkmenugetTask = (object :AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                drinkmenuList = drinkdb.drinkmenuDAO().drinkmenugetAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                drinkmenuSetRecyclerView()
            }
        }).execute()
    }

    private fun drinkmenuSetRecyclerView(){
        val com_DrinkMenu_Adapter = Com_DrinkMenu_Adapter(this)
        com_DrinkMenu_Adapter.submitList(drinkmenuList)

        drinkorderrecyclerview.apply{
            layoutManager = LinearLayoutManager(this@Completed_Order_Page)
            adapter = com_DrinkMenu_Adapter
        }

    }

    private fun drinkmenuDelete(drinkmenuEntity: DrinkmenuEntity){
        var drinkmenudeleteTask = (object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                drinkdb.drinkmenuDAO().drinkmenudelete(drinkmenuEntity)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                drinkmenugetAll()
            }

        }).execute()
    }

    private fun sidemenugetAll() {
        var sidemenugetTask =(object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                sidemenuList= sidedb.sidemenuDAO().sidemenugetALl()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                sidemenuSetRecyclerView()
            }

        }).execute()

    }

    private fun sidemenuSetRecyclerView() {
        val com_SideMenu_Adapter = Com_SideMenu_Adapter(this)
        com_SideMenu_Adapter.submitList(sidemenuList)

        sideorderrecyclerview.apply{
            layoutManager = LinearLayoutManager(this@Completed_Order_Page)
            adapter = com_SideMenu_Adapter
        }
    }

    private fun sidemenuDelete(sidemenuEntity: SidemenuEntity){
        var sidemenudeleteTask =(object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                sidedb.sidemenuDAO().sidemenuDelete(sidemenuEntity)
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

                sidemenugetAll()
            }

        }).execute()
    }

    private fun realmenugetAll() {
        val realmenugetallTask=(object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                realmenuList=realdb.realmenuDAO().realmenugetAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                realmenuSetRecyclerView()
            }

        }).execute()
    }

    private fun realmenuSetRecyclerView(){
        val com_RealMenu_Adapter =Com_RealMenu_Adapter(this)
        com_RealMenu_Adapter.submitList(realmenuList)

        realmenurecyclerview.apply {
            layoutManager = LinearLayoutManager(this@Completed_Order_Page)
            adapter = com_RealMenu_Adapter
        }

    }

    private fun realmenuDelete(realmenuEntity: RealmenuEntity){
        val realmenuDeleteTask=(object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                realdb.realmenuDAO().realmenuDelete(realmenuEntity)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                realmenugetAll()
            }


        }).execute()
    }

    private fun lastinsert(lastEntity: LastEntity){
        val lastinsertTask = (object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                lastdb.lastDAO().lastinsert(lastEntity)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                lastdb.lastDAO().lastgetAll()
            }
        })
    }



    override fun onrealmenuDeleteListner(realmenuEntity: RealmenuEntity) {
        realmenuDelete(realmenuEntity)
    }

    override fun onsidemenuDeleteListener(sidemenuEntity: SidemenuEntity) {
       sidemenuDelete(sidemenuEntity)
    }

    override fun ondrinkmenuDeleteListener(drinkmenuEntity: DrinkmenuEntity) {
       drinkmenuDelete(drinkmenuEntity)
    }




}