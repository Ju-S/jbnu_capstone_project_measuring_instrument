package com.example.measuring_instrument

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.measuring_instrument.databinding.FragmentIgrBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class IgrFragment : Fragment() {

    private var _binding: FragmentIgrBinding? = null
    private var btServer: BluetoothServer = BluetoothServer()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        btServer.accept()

        _binding = FragmentIgrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBackMulti.setOnClickListener {
            findNavController().navigate(R.id.action_igrFragment_to_multiFragment)
        }

        fun randFloat(from: Float, to: Float) : Float {
            var random = Random()
            return random.nextFloat() * (to - from) + from
        }
        fun randInt(from: Int, to: Int) : Int {
            var random = Random()
            return random.nextInt(to - from + 1) + from
        }

        fun editText(insul_resist: Float, igo: Float, igr: Float, AC_V: Float, freq: Int, leak_current: Float, load_current: Float){
            binding.igrInsulResist.setText(insul_resist.toString())
            binding.igrIgo.setText(igo.toString())
            binding.igrIgr.setText(igr.toString())
            binding.igrACV.setText(AC_V.toString())
            binding.igrFreq.setText(freq.toString())
            binding.igrLeakCurrent.setText(leak_current.toString())
            binding.igrLoadCurrent.setText(load_current.toString())
        }

        binding.buttonIgrGet.setOnClickListener {
            var insul_resist: Float = 0f
            var igo: Float = 0f
            var igr: Float = 0f
            var AC_V: Float = 0f
            var freq: Int = 0
            var leak_current: Float = 0f
            var load_current: Float = 0f
            insul_resist = randFloat(0.015f, 22f)
            igo = randFloat(0f, 2000f)
            igr = randFloat(0f, 2000f)
            AC_V = randFloat(0f, 500f)
            freq = randInt(20, 120)
            leak_current = randFloat(0f, 2000f)
            load_current = randFloat(0f, 300f)

            editText(insul_resist, igo, igr, AC_V, freq, leak_current, load_current)

            var sendData = arrayOf("IGR", insul_resist, igo, igr, AC_V, freq, leak_current, load_current)

            btServer.sendData(sendData.joinToString())
            Log.i("btdata", sendData.joinToString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}