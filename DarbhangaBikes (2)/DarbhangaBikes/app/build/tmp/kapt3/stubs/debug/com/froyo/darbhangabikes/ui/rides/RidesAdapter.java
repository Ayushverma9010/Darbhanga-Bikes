package com.froyo.darbhangabikes.ui.rides;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\bH\u0016J\u0018\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0014\u0010\u0011\u001a\u00020\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0013R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/froyo/darbhangabikes/ui/rides/RidesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/froyo/darbhangabikes/ui/rides/RidesAdapter$RideViewHolder;", "()V", "ridesList", "", "Lcom/froyo/darbhangabikes/data/model/Ride;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setRides", "rides", "", "RideViewHolder", "app_debug"})
public final class RidesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.froyo.darbhangabikes.ui.rides.RidesAdapter.RideViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.froyo.darbhangabikes.data.model.Ride> ridesList = null;
    
    public RidesAdapter() {
        super();
    }
    
    public final void setRides(@org.jetbrains.annotations.NotNull
    java.util.List<com.froyo.darbhangabikes.data.model.Ride> rides) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.froyo.darbhangabikes.ui.rides.RidesAdapter.RideViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.froyo.darbhangabikes.ui.rides.RidesAdapter.RideViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/froyo/darbhangabikes/ui/rides/RidesAdapter$RideViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "tvDate", "Landroid/widget/TextView;", "tvDrop", "tvPickup", "tvPrice", "tvStatus", "bind", "", "ride", "Lcom/froyo/darbhangabikes/data/model/Ride;", "app_debug"})
    public static final class RideViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvDate = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvPrice = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvPickup = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvDrop = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvStatus = null;
        
        public RideViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.froyo.darbhangabikes.data.model.Ride ride) {
        }
    }
}