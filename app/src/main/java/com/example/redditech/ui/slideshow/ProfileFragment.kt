package com.example.redditech.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.redditech.NavigationDrawerActivity
import com.example.redditech.R
import com.example.redditech.api.User
import com.example.redditech.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var profileModel: ProfileModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileModel =
            ViewModelProvider(this).get(ProfileModel::class.java)

        val activity: NavigationDrawerActivity = getActivity() as NavigationDrawerActivity
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val textUser: TextView = view.findViewById(R.id.usernameTextView)
        val textDescription: TextView = view.findViewById(R.id.textViewDescription)
        val image: ImageView = view.findViewById(R.id.imageViewProfile)
        val user: User = activity.getUser()

        Picasso.get().load(user.avatar).into(image)
        textUser.text = user.name
        textDescription.text = user.subreddit.public_description

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}