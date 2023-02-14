package com.example.loljokes.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loljokes.model.Joke
import com.example.loljokes.databinding.ItemJokeBinding

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokeViewHolder>() {

    inner class JokeViewHolder(val binding: ItemJokeBinding ) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var jokes: List<Joke>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = jokes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(ItemJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.binding.apply {
            val joke = jokes[position]
            tvTitle.text = joke.setup
            cardview.setOnClickListener {
                val animate1 = ObjectAnimator.ofFloat(tvTitle, "scaleX", 1f, 0f)
                val animate2 = ObjectAnimator.ofFloat(tvTitle, "scaleX", 0f, 1f)
                animate1.interpolator = DecelerateInterpolator()
                animate2.interpolator = AccelerateInterpolator()

                if (tvTitle.text == joke.setup) {
                    animate1.addListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            tvTitle.text = joke.punchline
                            animate2.start()
                        }
                    })
                    animate1.start()
                } else if (tvTitle.text == joke.punchline) {
                    animate1.addListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            tvTitle.text = joke.setup
                            animate2.start()
                        }
                    })
                    animate1.start()
                }
            }
        }
    }
}