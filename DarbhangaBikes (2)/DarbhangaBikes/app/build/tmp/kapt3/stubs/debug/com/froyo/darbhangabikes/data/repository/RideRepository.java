package com.froyo.darbhangabikes.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0006H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\"\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00062\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00140\u0016J$\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\u0006\u0010\u0019\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ&\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\u0086@\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/froyo/darbhangabikes/data/repository/RideRepository;", "", "()V", "COMMISSION_PERCENTAGE", "", "TAG", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "calculateDriverEarning", "totalFare", "calculateEstimatedFare", "distanceInMeters", "cancelRide", "Lkotlin/Result;", "", "rideId", "cancelRide-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listenToRide", "", "onRideUpdate", "Lkotlin/Function1;", "Lcom/froyo/darbhangabikes/data/model/Ride;", "requestRide", "ride", "requestRide-gIAlu-s", "(Lcom/froyo/darbhangabikes/data/model/Ride;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "simulateDriverMovement", "startLat", "startLng", "(Ljava/lang/String;DDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class RideRepository {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String TAG = "RideRepository";
    private final double COMMISSION_PERCENTAGE = 15.0;
    
    public RideRepository() {
        super();
    }
    
    public final double calculateEstimatedFare(double distanceInMeters) {
        return 0.0;
    }
    
    public final double calculateDriverEarning(double totalFare) {
        return 0.0;
    }
    
    public final void listenToRide(@org.jetbrains.annotations.NotNull
    java.lang.String rideId, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.froyo.darbhangabikes.data.model.Ride, kotlin.Unit> onRideUpdate) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object simulateDriverMovement(@org.jetbrains.annotations.NotNull
    java.lang.String rideId, double startLat, double startLng, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}