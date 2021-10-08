package com.amin.newalbumstask.ui.albums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amin.newalbumstask.adapter.PhotosRecyclerAdapter
import com.amin.newalbumstask.data.model.Photo
import com.amin.newalbumstask.databinding.FragmentAlbumDetailsBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class AlbumDetailsFragment : DaggerFragment(), HasAndroidInjector,
    PhotosRecyclerAdapter.Interaction {
    private var _binding: FragmentAlbumDetailsBinding? = null
    private val binding get() = _binding!!
    var mLayoutManager: GridLayoutManager? = null

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    var currentPhotosList: List<Photo>? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AlbumDetailsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(AlbumDetailsViewModel::class.java)
    }

    private val photosAdapter: PhotosRecyclerAdapter by lazy {
        PhotosRecyclerAdapter(this)
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
        _binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            photosData.observe(viewLifecycleOwner, { photos ->

                currentPhotosList = photos
                handleSearchQuery()
                binding.avi.hide()
                binding.searchView.visibility = View.VISIBLE
                binding.imagesRv.visibility = View.VISIBLE
                photosAdapter.submitList(photos)

            })

            error.observe(viewLifecycleOwner, {

                binding.avi.hide()
                Toast.makeText(context, "${it?.message}", Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val album = AlbumDetailsFragmentArgs.fromBundle(it).album
            binding.albumDetailsTitle.text = album.title

            initRecyclerView()

            viewModel.getPhotosData(album.id.toString())
        }

    }

    private fun initRecyclerView() {
        binding.imagesRv.apply {
            mLayoutManager = GridLayoutManager(requireActivity(), 3)
            layoutManager = mLayoutManager
            adapter = photosAdapter

        }
    }

    private fun handleSearchQuery() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText.trim().isNotEmpty()) {
                    currentPhotosList?.let { lastList ->
                        photosAdapter.submitList(lastList.filter { it.title.contains(newText) })
                    }

                } else {
                    currentPhotosList?.let {
                        photosAdapter.submitList(it)
                        mLayoutManager?.scrollToPosition(0)
                    }

                }

                return false
            }
        })
    }

    override fun onItemSelected(position: Int, item: Photo) {
        val action =
            AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToPhotoViewerFragment(item.thumbnailUrl)
        findNavController().navigate(action)
    }


}