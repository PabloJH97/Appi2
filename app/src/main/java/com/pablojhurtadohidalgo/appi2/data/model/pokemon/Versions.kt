package com.pablojhurtadohidalgo.appi2.data.model.pokemon

import com.google.gson.annotations.SerializedName

data class Versions(
    @SerializedName("generation-i: GenerationI")
    val generationI: GenerationI,
    @SerializedName("generation-ii: GenerationIi")
    val generationIi: GenerationIi,
    @SerializedName("generation-iii: GenerationIii")
    val generationIii: GenerationIii,
    @SerializedName("generation-iv: GenerationIv")
    val generationIv: GenerationIv,
    @SerializedName("generation-v: GenerationV")
    val generationV: GenerationV,
    @SerializedName("generation-vi: GenerationVi")
    val generationVi: GenerationVi,
    @SerializedName("generation-vii: GenerationVii")
    val generationVii: GenerationVii,
    @SerializedName("generation-viii: GenerationViii")
    val generationViii: GenerationViii
)