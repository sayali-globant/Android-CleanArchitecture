package com.example.marvels.presentation.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvels.R
import com.example.marvels.common.listeners.OnActionListener
import com.example.marvels.data.entity.CharacterDetail
import com.example.marvels.domain.utils.setImage
import com.example.marvels.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_character_detail.*


class CharacterDetailFragment : BaseFragment() {

    private var mView: View? = null
    private var mCharacterDetail: CharacterDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }

    private fun getArgs() {
        arguments?.let {
            mCharacterDetail = it.getParcelable(ARG_CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_character_detail, container, false)
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
    }


    private fun setupUI() {
        mCharacterDetail?.let {
            it.thumbnail?.let { thumb ->
                imageViewCharacterDetail.setImage(
                    requireContext(),
                    thumb.path + "." + thumb.extension
                )
            }
            textViewCharacterDetailName.text = it.name
            textViewCharacterDetailDescription.text = it.description
        }
    }

    companion object {

        private const val ARG_CHARACTER = "character"
        fun newInstance(characterDetail: CharacterDetail) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, characterDetail)
                }
            }
    }

}