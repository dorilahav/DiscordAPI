package com.dorilahav.api.utils;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.dv8tion.jda.core.utils.Checks;

@AllArgsConstructor
public class CollectionBuilder<C extends Collection<T>, T> {
	
	private C
			collection;
		
	public CollectionBuilder(CollectionBuilder<C, T> builder) {
		this.collection = builder.build();
	}
	
	public CollectionBuilder<C, T> add(@NonNull T item) {
		this.collection.add(item);
		return this;
	}
	
	public CollectionBuilder<C, T> addAll(@NonNull C collection) {
		this.collection.addAll(collection);
		return this;
	}
	
	public CollectionBuilder<C, T> remove(@NonNull T item) {
		this.collection.remove(item);
		return this;
	}
	
	public C build() {
		return this.collection;
	}
	
	@SafeVarargs
	public static <C extends Collection<T>, T> C combine(@NonNull C... collections) {
		Checks.notEmpty(collections, "collections");
		
		if (collections.length == 1)
			return collections[0];
		
		CollectionBuilder<C, T> builder = new CollectionBuilder<>(collections[0]);
		for (int i = 1; i < collections.length; i++)
			builder.addAll(collections[i]);
		
		return builder.build();
	}
}
