package com.froyo.darbhangabikes.data.model;

@com.google.firebase.firestore.IgnoreExtraProperties
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b:\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u00dd\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001b\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010 J\t\u0010?\u001a\u00020\u0003H\u00c6\u0003J\t\u0010@\u001a\u00020\fH\u00c6\u0003J\t\u0010A\u001a\u00020\fH\u00c6\u0003J\t\u0010B\u001a\u00020\u0012H\u00c6\u0003J\t\u0010C\u001a\u00020\u0014H\u00c6\u0003J\t\u0010D\u001a\u00020\u0016H\u00c6\u0003J\t\u0010E\u001a\u00020\u0018H\u00c6\u0003J\t\u0010F\u001a\u00020\u0018H\u00c6\u0003J\t\u0010G\u001a\u00020\u001bH\u00c6\u0003J\t\u0010H\u001a\u00020\u001bH\u00c6\u0003J\t\u0010I\u001a\u00020\u001bH\u00c6\u0003J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\t\u0010K\u001a\u00020\u001bH\u00c6\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010N\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010O\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010P\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\t\u0010R\u001a\u00020\fH\u00c6\u0003J\t\u0010S\u001a\u00020\u000eH\u00c6\u0003J\u00e1\u0001\u0010T\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\f2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u00182\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010\u001d\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u001b2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010U\u001a\u00020V2\b\u0010W\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010X\u001a\u00020\u000eH\u00d6\u0001J\t\u0010Y\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0010\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u001e\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0011\u0010\u001a\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\"R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0013\u0010\u001f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010*R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u000f\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010$R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010(R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010*R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010(R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010(R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010,R\u0011\u0010\u001d\u001a\u00020\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010\"R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010>\u00a8\u0006Z"}, d2 = {"Lcom/froyo/darbhangabikes/data/model/Ride;", "", "rideId", "", "riderId", "driverId", "pickupLocation", "Lcom/google/firebase/firestore/GeoPoint;", "dropLocation", "pickupAddress", "dropAddress", "estimatedDistance", "", "estimatedDuration", "", "estimatedFare", "actualFare", "status", "Lcom/froyo/darbhangabikes/data/model/RideStatus;", "paymentMethod", "Lcom/froyo/darbhangabikes/data/model/PaymentMethod;", "paymentStatus", "Lcom/froyo/darbhangabikes/data/model/PaymentStatus;", "riderRating", "", "driverRating", "createdAt", "", "acceptedAt", "startedAt", "completedAt", "driverLocation", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/firebase/firestore/GeoPoint;Lcom/google/firebase/firestore/GeoPoint;Ljava/lang/String;Ljava/lang/String;DIDDLcom/froyo/darbhangabikes/data/model/RideStatus;Lcom/froyo/darbhangabikes/data/model/PaymentMethod;Lcom/froyo/darbhangabikes/data/model/PaymentStatus;FFJJJJLcom/google/firebase/firestore/GeoPoint;)V", "getAcceptedAt", "()J", "getActualFare", "()D", "getCompletedAt", "getCreatedAt", "getDriverId", "()Ljava/lang/String;", "getDriverLocation", "()Lcom/google/firebase/firestore/GeoPoint;", "getDriverRating", "()F", "getDropAddress", "getDropLocation", "getEstimatedDistance", "getEstimatedDuration", "()I", "getEstimatedFare", "getPaymentMethod", "()Lcom/froyo/darbhangabikes/data/model/PaymentMethod;", "getPaymentStatus", "()Lcom/froyo/darbhangabikes/data/model/PaymentStatus;", "getPickupAddress", "getPickupLocation", "getRideId", "getRiderId", "getRiderRating", "getStartedAt", "getStatus", "()Lcom/froyo/darbhangabikes/data/model/RideStatus;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Ride {
    @com.google.firebase.firestore.DocumentId
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rideId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String riderId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String driverId = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.firestore.GeoPoint pickupLocation = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.firestore.GeoPoint dropLocation = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pickupAddress = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String dropAddress = null;
    private final double estimatedDistance = 0.0;
    private final int estimatedDuration = 0;
    private final double estimatedFare = 0.0;
    private final double actualFare = 0.0;
    @org.jetbrains.annotations.NotNull
    private final com.froyo.darbhangabikes.data.model.RideStatus status = null;
    @org.jetbrains.annotations.NotNull
    private final com.froyo.darbhangabikes.data.model.PaymentMethod paymentMethod = null;
    @org.jetbrains.annotations.NotNull
    private final com.froyo.darbhangabikes.data.model.PaymentStatus paymentStatus = null;
    private final float riderRating = 0.0F;
    private final float driverRating = 0.0F;
    private final long createdAt = 0L;
    private final long acceptedAt = 0L;
    private final long startedAt = 0L;
    private final long completedAt = 0L;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.firestore.GeoPoint driverLocation = null;
    
    public Ride(@org.jetbrains.annotations.NotNull
    java.lang.String rideId, @org.jetbrains.annotations.NotNull
    java.lang.String riderId, @org.jetbrains.annotations.NotNull
    java.lang.String driverId, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint pickupLocation, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint dropLocation, @org.jetbrains.annotations.NotNull
    java.lang.String pickupAddress, @org.jetbrains.annotations.NotNull
    java.lang.String dropAddress, double estimatedDistance, int estimatedDuration, double estimatedFare, double actualFare, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.RideStatus status, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.PaymentMethod paymentMethod, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.PaymentStatus paymentStatus, float riderRating, float driverRating, long createdAt, long acceptedAt, long startedAt, long completedAt, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint driverLocation) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRideId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRiderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDriverId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint getPickupLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint getDropLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPickupAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDropAddress() {
        return null;
    }
    
    public final double getEstimatedDistance() {
        return 0.0;
    }
    
    public final int getEstimatedDuration() {
        return 0;
    }
    
    public final double getEstimatedFare() {
        return 0.0;
    }
    
    public final double getActualFare() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.RideStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.PaymentMethod getPaymentMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.PaymentStatus getPaymentStatus() {
        return null;
    }
    
    public final float getRiderRating() {
        return 0.0F;
    }
    
    public final float getDriverRating() {
        return 0.0F;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getAcceptedAt() {
        return 0L;
    }
    
    public final long getStartedAt() {
        return 0L;
    }
    
    public final long getCompletedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint getDriverLocation() {
        return null;
    }
    
    public Ride() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.RideStatus component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.PaymentMethod component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.PaymentStatus component14() {
        return null;
    }
    
    public final float component15() {
        return 0.0F;
    }
    
    public final float component16() {
        return 0.0F;
    }
    
    public final long component17() {
        return 0L;
    }
    
    public final long component18() {
        return 0L;
    }
    
    public final long component19() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final long component20() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.firestore.GeoPoint component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.froyo.darbhangabikes.data.model.Ride copy(@org.jetbrains.annotations.NotNull
    java.lang.String rideId, @org.jetbrains.annotations.NotNull
    java.lang.String riderId, @org.jetbrains.annotations.NotNull
    java.lang.String driverId, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint pickupLocation, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint dropLocation, @org.jetbrains.annotations.NotNull
    java.lang.String pickupAddress, @org.jetbrains.annotations.NotNull
    java.lang.String dropAddress, double estimatedDistance, int estimatedDuration, double estimatedFare, double actualFare, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.RideStatus status, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.PaymentMethod paymentMethod, @org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.data.model.PaymentStatus paymentStatus, float riderRating, float driverRating, long createdAt, long acceptedAt, long startedAt, long completedAt, @org.jetbrains.annotations.Nullable
    com.google.firebase.firestore.GeoPoint driverLocation) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}