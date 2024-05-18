package ru.mirea.pillmeapplication.addPill

import ru.mirea.pillmeapplication.addPill.BaseFragment

interface FragmentReplacer {
    fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean = true)
    fun replaceDef(position: Int, isNotify: Boolean = true) : BaseFragment
    fun replaceCurrentToDef()

    var lastReplacedPos: Int
}