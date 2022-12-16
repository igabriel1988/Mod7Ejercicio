package dgtic.unam.modulosieta

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import dgtic.unam.modulosieta.databinding.ActivityMainMusicaBinding
import java.io.File

class activity_main_musica : AppCompatActivity()
{
    private lateinit var archivos  : ArrayList<ModeloMusica>
    private lateinit var binding   : ActivityMainMusicaBinding
    private lateinit var adapt      : RecipeAdapterMusica
    private  var mediaPlayer       : MediaPlayer? = null
    private var indice             : Int = 0



    override fun onCreate( savedInstanceState: Bundle? )
        {
            super.onCreate( savedInstanceState )
            setContentView( R.layout.activity_main_musica )

            supportActionBar?.setDisplayHomeAsUpEnabled( true )
            supportActionBar?.setHomeButtonEnabled( true )

            binding= ActivityMainMusicaBinding.inflate( layoutInflater )
            setContentView( binding.root )

            val stadoSD : String = Environment.getExternalStorageState()
            if( stadoSD == Environment.MEDIA_MOUNTED )
                    {
                            println(    "sistema ::: " +
                                        Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MUSIC ).path )
                           println( "sistema2 ::: " + this.getExternalFilesDir( null ) )
                            val ff =
                                    File( Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MUSIC ).path )

                        println(    "sistema /////////////////// ::: " + ff.length() );
                            archivos = ArrayList()
                        ff.listFiles()?.forEach {
                            if( it.isFile ){
                                println( it )
                                archivos.add( ModeloMusica( it.name, R.drawable.musica_img, it.path ) )
                            }
                        }

                            archivos.add( ModeloMusica( "Magenta_Moon_Part_II.mp3", R.drawable.musica_img,
                                                            "https://files.freemusicarvhive.org/storage-freemusicarchive-org/music/no_curator/Line_Noise/Magenta_Moon/Line_Noise_-_01_-_Magenta_Moon_Part_II.mp3") )
                    }

                    adapt = RecipeAdapterMusica( this, archivos )
                    binding.list.adapter = adapt

                    binding.play.setOnClickListener{
                            if( mediaPlayer == null )
                                {
                                    mediaPlayer = MediaPlayer().apply {
                                        setDataSource( archivos[indice].path )
                                        prepare()
                                        start()
                                    }
                                }
                            else
                                {
                                    mediaPlayer!!.start()
                                }
                    }

                binding.stop.setOnClickListener{
                            if( mediaPlayer != null )
                                {
                                    mediaPlayer!!.release()
                                    mediaPlayer = null
                                }
                        }
                binding.pause.setOnClickListener {
                    if( mediaPlayer != null )
                        {
                            mediaPlayer!!.pause()
                        }
                }

                binding.path.setOnClickListener {
                    Toast.makeText( this, archivos.get( indice ).path, Toast.LENGTH_SHORT ).show()
                }

                binding.list.setOnItemClickListener{ parent, view, position, id ->
                    val data : ModeloMusica = archivos.get( position )
                    indice = position
                    if( mediaPlayer == null )
                            {
                                mediaPlayer = MediaPlayer().apply {
                                    setDataSource( data.path )
                                    prepare()
                                    start()
                                }
                            }
                    else
                        {
                            mediaPlayer!!.release()
                            mediaPlayer = MediaPlayer().apply {
                                setDataSource( data.path )
                                prepare()
                                start()
                            }
                        }
                    Toast.makeText( this, data.nameFile, Toast.LENGTH_SHORT ).show()
                }

        }

    override fun onSupportNavigateUp(): Boolean
        {
            onBackPressed()
            return true
        }
}