// Generated by view binder compiler. Do not edit!
package com.example.examenalejandromarino.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.examenalejandromarino.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddequipoBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton buttonAdd;

  @NonNull
  public final Guideline guideBottom;

  @NonNull
  public final Guideline guideCenter;

  @NonNull
  public final Guideline guideLeft;

  @NonNull
  public final Guideline guideRight;

  @NonNull
  public final Guideline guideTop;

  @NonNull
  public final TextInputEditText number;

  @NonNull
  public final TextInputLayout numberField;

  @NonNull
  public final TextInputLayout textField;

  @NonNull
  public final TextInputEditText textNacionalidad;

  @NonNull
  public final TextInputEditText textName;

  @NonNull
  public final TextInputLayout textoField;

  private ActivityAddequipoBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton buttonAdd, @NonNull Guideline guideBottom,
      @NonNull Guideline guideCenter, @NonNull Guideline guideLeft, @NonNull Guideline guideRight,
      @NonNull Guideline guideTop, @NonNull TextInputEditText number,
      @NonNull TextInputLayout numberField, @NonNull TextInputLayout textField,
      @NonNull TextInputEditText textNacionalidad, @NonNull TextInputEditText textName,
      @NonNull TextInputLayout textoField) {
    this.rootView = rootView;
    this.buttonAdd = buttonAdd;
    this.guideBottom = guideBottom;
    this.guideCenter = guideCenter;
    this.guideLeft = guideLeft;
    this.guideRight = guideRight;
    this.guideTop = guideTop;
    this.number = number;
    this.numberField = numberField;
    this.textField = textField;
    this.textNacionalidad = textNacionalidad;
    this.textName = textName;
    this.textoField = textoField;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddequipoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddequipoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_addequipo, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddequipoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonAdd;
      ImageButton buttonAdd = ViewBindings.findChildViewById(rootView, id);
      if (buttonAdd == null) {
        break missingId;
      }

      id = R.id.guideBottom;
      Guideline guideBottom = ViewBindings.findChildViewById(rootView, id);
      if (guideBottom == null) {
        break missingId;
      }

      id = R.id.guideCenter;
      Guideline guideCenter = ViewBindings.findChildViewById(rootView, id);
      if (guideCenter == null) {
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

      id = R.id.number;
      TextInputEditText number = ViewBindings.findChildViewById(rootView, id);
      if (number == null) {
        break missingId;
      }

      id = R.id.numberField;
      TextInputLayout numberField = ViewBindings.findChildViewById(rootView, id);
      if (numberField == null) {
        break missingId;
      }

      id = R.id.textField;
      TextInputLayout textField = ViewBindings.findChildViewById(rootView, id);
      if (textField == null) {
        break missingId;
      }

      id = R.id.textNacionalidad;
      TextInputEditText textNacionalidad = ViewBindings.findChildViewById(rootView, id);
      if (textNacionalidad == null) {
        break missingId;
      }

      id = R.id.textName;
      TextInputEditText textName = ViewBindings.findChildViewById(rootView, id);
      if (textName == null) {
        break missingId;
      }

      id = R.id.textoField;
      TextInputLayout textoField = ViewBindings.findChildViewById(rootView, id);
      if (textoField == null) {
        break missingId;
      }

      return new ActivityAddequipoBinding((ConstraintLayout) rootView, buttonAdd, guideBottom,
          guideCenter, guideLeft, guideRight, guideTop, number, numberField, textField,
          textNacionalidad, textName, textoField);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}