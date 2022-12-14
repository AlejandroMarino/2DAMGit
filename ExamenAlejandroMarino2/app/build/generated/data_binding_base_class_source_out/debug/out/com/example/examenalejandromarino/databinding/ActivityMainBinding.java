// Generated by view binder compiler. Do not edit!
package com.example.examenalejandromarino.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.examenalejandromarino.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView equipos;

  @NonNull
  public final Guideline guideBottom;

  @NonNull
  public final Guideline guideLeft;

  @NonNull
  public final Guideline guideRight;

  @NonNull
  public final Guideline guideTop;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull RecyclerView equipos,
      @NonNull Guideline guideBottom, @NonNull Guideline guideLeft, @NonNull Guideline guideRight,
      @NonNull Guideline guideTop) {
    this.rootView = rootView;
    this.equipos = equipos;
    this.guideBottom = guideBottom;
    this.guideLeft = guideLeft;
    this.guideRight = guideRight;
    this.guideTop = guideTop;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.equipos;
      RecyclerView equipos = ViewBindings.findChildViewById(rootView, id);
      if (equipos == null) {
        break missingId;
      }

      id = R.id.guideBottom;
      Guideline guideBottom = ViewBindings.findChildViewById(rootView, id);
      if (guideBottom == null) {
        break missingId;
      }

      id = R.id.guideLeft;
      Guideline guideLeft = ViewBindings.findChildViewById(rootView, id);
      if (guideLeft == null) {
        break missingId;
      }

      id = R.id.guideRight;
      Guideline guideRight = ViewBindings.findChildViewById(rootView, id);
      if (guideRight == null) {
        break missingId;
      }

      id = R.id.guideTop;
      Guideline guideTop = ViewBindings.findChildViewById(rootView, id);
      if (guideTop == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, equipos, guideBottom, guideLeft,
          guideRight, guideTop);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
