package com.amin.newalbumstask.ui.albums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amin.newalbumstask.R
import com.amin.newalbumstask.databinding.FragmentPhotoViewerBinding
import com.amin.newalbumstask.utils.getImageUrlForGlide
import com.bumptech.glide.Glide
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class PhotoViewerFragment : DaggerFragment(), HasAndroidInjector {

    private var _binding: FragmentPhotoViewerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

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
        _binding = FragmentPhotoViewerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val imageUrl = PhotoViewerFragmentArgs.fromBundle(it).imageUrl


            Glide.with(requireActivity()).load(imageUrl.getImageUrlForGlide()).into(binding.photoIv)
            binding.shareIv.setOnClickListener {
                shareImageUrl(imageUrl)
            }

        }


    }

    private fun shareImageUrl(imageUrl:String){

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, imageUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_image))
        startActivity(shareIntent)
    }



}