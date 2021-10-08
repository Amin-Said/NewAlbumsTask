package com.amin.newalbumstask.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amin.newalbumstask.adapter.AlbumsRecyclerAdapter
import com.amin.newalbumstask.data.model.Album
import com.amin.newalbumstask.databinding.FragmentProfileBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileFragment : DaggerFragment(), HasAndroidInjector, AlbumsRecyclerAdapter.Interaction {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(ProfileViewModel::class.java)
    }

    private val albumsAdapter: AlbumsRecyclerAdapter by lazy {
        AlbumsRecyclerAdapter(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            currentUser.observe(viewLifecycleOwner, { currentUser ->
                binding.userNameTv.text = currentUser.name
                binding.userAddressTv.text =
                    "${currentUser.address.street} , ${currentUser.address.city} , ${currentUser.address.suite} , ${currentUser.address.zipcode}"

            })

            albumsData.observe(viewLifecycleOwner, { albums ->
                binding.avi.hide()
                binding.userNameTv.visibility = View.VISIBLE
                binding.userAddressTv.visibility = View.VISIBLE
                binding.albumsRv.visibility = View.VISIBLE
                binding.myAlbumsTitleTv.visibility = View.VISIBLE
                albumsAdapter.submitList(albums)

            })

            error.observe(viewLifecycleOwner, {

                binding.avi.smoothToHide()
                Toast.makeText(context, "${it?.message}", Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.albumsRv.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = albumsAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Album) {
        val action = ProfileFragmentDirections.actionProfileFragmentToAlbumDetailsFragment(item)
        findNavController().navigate(action)
    }


}