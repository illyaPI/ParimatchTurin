package com.parimachek.dos.com.repositoryturin.viewbindingturin

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivityTurin<VB : ViewBinding>(
  private val bindingInflaterTurin: (LayoutInflater) -> VB,
) : AppCompatActivity() {

  protected lateinit var bndTurin: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    bndTurin = bindingInflaterTurin(layoutInflater)
    setContentView(bndTurin.root)
  }
}