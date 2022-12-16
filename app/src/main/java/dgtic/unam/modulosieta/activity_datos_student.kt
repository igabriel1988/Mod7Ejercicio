package dgtic.unam.modulosieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import dgtic.unam.modulosieta.databinding.ActivityDatosStudentBinding
import org.json.JSONObject


class activity_datos_student : AppCompatActivity()
{
    private lateinit var binding: ActivityDatosStudentBinding
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url:String
    private val  ipPuerto = "192.168.210.1:8080"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_datos_student )

        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        supportActionBar?.setHomeButtonEnabled( true )

        //binding= Activity.inflate( layoutInflater )
        setContentView( binding.root )



        binding.restjsonid.setOnClickListener {
            studiantID()
        }
    }

    ////
    private fun studiantID()
    {
        val urlJSON = "http://" + ipPuerto + "/id/"+binding.searchText.text.toString()
        var cadena = ""
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            urlJSON,
            null,
            Response.Listener<JSONObject> { response ->
                binding.outText.text = response.get( "cuenta" )
                    .toString() + "----------" + response.get( "nombre" ).toString() + "\n"
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la informacion, revise su conexion"
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    override fun onSupportNavigateUp(): Boolean
    {
        onBackPressed()
        return true
    }

}