package com.michael.newsdetail.domain.contract

import com.michael.base.contract.BaseViewAction

interface NewsDetailViewAction : BaseViewAction {
    data class GetNewsDetail(val id: Int) : NewsDetailViewAction
}