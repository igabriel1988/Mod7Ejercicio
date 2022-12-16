package dgtic.unam.modulosieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import dgtic.unam.modulosieta.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import dgtic.unam.modulosieta.databinding.ActivityMainAddStudentBinding
import org.json.JSONArray
import org.json.JSONObject


class main_activity_add_student : AppCompatActivity()
{

    private lateinit var binding: ActivityMainAddStudentBinding
    private lateinit var drawer : DrawerLayout
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url:String
    private val  ipPuerto = "192.168.210.1:8080"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main_add_student )

        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        supportActionBar?.setHomeButtonEnabled( true )

        binding= ActivityMainAddStudentBinding.inflate( layoutInflater )
        setContentView( binding.root )

        volleyAPI= VolleyAPI( this )
        binding.restjsonadd.setOnClickListener {
            studiantADD()
        }
    }

    private fun  studiantADD()
    {
        val urlJSON = "http://" + ipPuerto + "/agregarestudiante"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                ( 0 until response.length() ).forEach {
                    val estudiante = response.getJSONObject( it )
                    val materia = estudiante.getJSONArray("materias" )
                    cadena += estudiante.get( "cuenta" ).toString() + "<"
                    ( 0 until materia.length() ).forEach {
                        val datos = materia.getJSONObject( it )
                        cadena += datos.get( "nombre" ).toString() + "**"+datos.get( "creditos" )
                            .toString()+"---"
                    }
                    cadena += "> \n"
                }
                binding.outText.text = cadena
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

            override fun getBody(): ByteArray
            {
                val estudiante = JSONObject()
                estudiante.put( "cuenta", "A000" )
                estudiante.put( "nombre", "Android" )
                estudiante.put( "edad", "200" )

                val materias = JSONArray()
                val itemMaterias = JSONObject()
                itemMaterias.put( "id", "1" )
                itemMaterias.put( "nombre", "Nueva materia1" )
                itemMaterias.put( "creditos", "100" )
                materias.put( itemMaterias )

                ////
                itemMaterias.put( "id", "2" )
                itemMaterias.put( "nombre", "Nueva materia2" )
                itemMaterias.put( "creditos", "100" )
                materias.put( itemMaterias )

                estudiante.put( "materias", materias )
                return estudiante.toString().toByteArray( charset = Charsets.UTF_8 )
            }

            override fun getMethod(): Int {
                return Method.POST
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