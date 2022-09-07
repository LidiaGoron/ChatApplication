package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ToDoAdapter( //in constructor specificam o list de ToDo Items
    private val todos: MutableList<ToDo>
):RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>(){ // clasa ToDo mosteneste RecycleView adapter pentru ca avem nevoie de adapterul pt acest tool
    class TodoViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) //referentiaza fisierul xml unde avem vederile pentru item urile noastre
                                                                            // cu acest holder avem acces la views
                                                                            //itemView contine vederile

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate( //layoutinflater va converti codul xml items_todo intr o clasa ce o putem folosi aici
                                                         // functia inflate va converti xml ul intr-o vedere reala ce o putem folosi in cod

                //functia are 3 parametrii :
                // 1. Resource Id (fiecare fisier xml are un id interior)
                R.layout.items_todo, //referentiem id-ul cu R (resource) . Noi vrem aici layout-ul fiecarui item din lista
                //view group
                parent,
                // don't want to atach this to root layout
                false
            )

        )

    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) { // avem viewholder si pozitia lui ca parametrii
        // acesta functie va lega datele din lista noastra cu vederile din lista
        // acesta functie va fi apelata cand un newHolder este vizibil deci o noua lista de items in lista noastra este vizibila
        // in aceasta functie vom stabili ce vrem pentru items_todo de ex de text vrem pentru TextView sau daca vrem sa verificam
        //check box-ul sau nu
        //așa că, practic, folosim lista noastră și setăm datele acelei liste la views-urile noastre
        val currentTodo=todos[position] //item ul curent
        holder.itemView.apply{
            tvToDoTitle


        }
        

    }

    override fun getItemCount(): Int { //returneaza nr de items pe care le avem in lista
        return todos.size
    }
}