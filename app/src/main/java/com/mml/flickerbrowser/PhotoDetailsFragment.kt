package com.mml.flickerbrowser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mml.flickerbrowser.databinding.FragmentPhotoDetailsBinding
import com.squareup.picasso.Picasso


class PhotoDetailsFragment : Fragment(R.layout.fragment_photo_details) {

    private lateinit var binding: FragmentPhotoDetailsBinding
    private val args: PhotoDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)

        val photo = args.photo

        binding.apply {
            photoTitle.text = resources.getString(R.string.photo_title_text, photo.title)
            photoAuthor.text = photo.author
            photoTags.text = resources.getString(R.string.photo_tags_text, photo.tags)

            Picasso.get().load(photo.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(binding.photoImage)
        }


        return binding.root
    }

}