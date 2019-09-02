package com.teayork.module_main.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.view.View
import android.widget.CompoundButton
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.mvp.view.MvpActivity
import com.teayork.common_base.utils.DateUtils
import com.teayork.common_base.utils.LogUtils
import com.teayork.common_base.utils.toast.ToastUtils
import com.teayork.module_main.R
import com.teayork.module_main.app.Main_App
import com.teayork.module_main.busevent.BusEvent
import com.teayork.module_main.dao.CommunityBeanDao
import com.teayork.module_main.dao.DaoSession
import com.teayork.module_main.daobean.CommunityBean
import com.teayork.module_main.mvp.contact.PostCommunityContact
import com.teayork.module_main.mvp.presenter.PostCommunityPresenter
import kotlinx.android.synthetic.main.main_activity_community.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

/**
 * author：pengzhixian on 2019-08-19 11:39
 * e-mail：759560522@qq.com
 */
class PostCommunityActivity : MvpActivity<PostCommunityPresenter>(), PostCommunityContact.IView,
    BGASortableNinePhotoLayout.Delegate {



    var daoSession: DaoSession? =null
    override fun onClickNinePhotoItem(
        sortableNinePhotoLayout: BGASortableNinePhotoLayout?,
        view: View?,
        position: Int,
        model: String?,
        models: java.util.ArrayList<String>?
    ) {
        val photoPickerPreviewIntent = BGAPhotoPickerPreviewActivity.IntentBuilder(this)
            .previewPhotos(models) // 当前预览的图片路径集合
            .selectedPhotos(models) // 当前已选中的图片路径集合
            .maxChooseCount(snpl_moment_add_photos.getMaxItemCount()) // 图片选择张数的最大值
            .currentPosition(position) // 当前预览图片的索引
            .isFromTakePhoto(false) // 是否是拍完照后跳转过来
            .build()
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW)
    }

    override fun onClickAddNinePhotoItem(
        sortableNinePhotoLayout: BGASortableNinePhotoLayout?,
        view: View?,
        position: Int,
        models: java.util.ArrayList<String>?
    ) {
        choicePhotoWrapper()

    }

    private fun choicePhotoWrapper() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            val takePhotoDir = File(Environment.getExternalStorageDirectory(), "pzxApp")

            val photoPickerIntent = BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(if (cb_moment_add_take_photo.isChecked()) takePhotoDir else null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(snpl_moment_add_photos.getMaxItemCount() - snpl_moment_add_photos.getItemCount()) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build()
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO)
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, *perms)
        }


    }

    override fun onNinePhotoItemExchanged(
        sortableNinePhotoLayout: BGASortableNinePhotoLayout?,
        fromPosition: Int,
        toPosition: Int,
        models: java.util.ArrayList<String>?
    ) {

        ToastUtils.show("aaaa")

    }

    override fun onClickDeleteNinePhotoItem(
        sortableNinePhotoLayout: BGASortableNinePhotoLayout?,
        view: View?,
        position: Int,
        model: String?,
        models: java.util.ArrayList<String>?
    ) {
        snpl_moment_add_photos.removeItem(position)
    }


    companion object {
        val PRC_PHOTO_PICKER: Int = 11
        val RC_CHOOSE_PHOTO: Int = 111
        val RC_PHOTO_PREVIEW: Int = 222


    }

    override fun getLayoutId(): Int {

        return R.layout.main_activity_community

    }

    override fun initView() {
        daoSession= Main_App.instance().getDaoSession()


        snpl_moment_add_photos.setDelegate(this)
//        snpl_moment_add_photos.data

        cb_moment_add_single_choice.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                snpl_moment_add_photos.setData(null)
                snpl_moment_add_photos.setMaxItemCount(1)
            } else {
                snpl_moment_add_photos.setMaxItemCount(9)
            }
        })
        cb_moment_add_editable.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            snpl_moment_add_photos.setEditable(
                checked
            )
        })
        cb_moment_add_plus.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            snpl_moment_add_photos.setPlusEnable(
                checked
            )
        })
        cb_moment_add_sortable.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, checked ->
            snpl_moment_add_photos.setSortable(
                checked
            )
        })


        tv_moment_add_choice_photo.setOnClickListener {

            initcheckPermission()

        }

        tv_moment_add_publish.setOnClickListener {

//            LogUtils.e("看看数据",snpl_moment_add_photos.data.toString())

           var communityBean: CommunityBean= CommunityBean(null,null,"小贤一号","我今天看见了。点点点。。。。。啊哈哈哈",DateUtils.getCurrentDateTime("YYYY-MM-DD-HH-MM-SS"),snpl_moment_add_photos.data)

            daoSession!!.insert(communityBean)
            EventBus.getDefault().post(BusEvent.PostDynamicEvent(communityBean))
            this.finish()




        }


    }

    override fun registerPresenter() = PostCommunityPresenter::class.java

    override fun setData(list: ArrayList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun initcheckPermission() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        EasyPermissions.requestPermissions(this, "KotlinMvp应用需要以下权限，请允许", 0, *perms)

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        super.onPermissionsGranted(requestCode, perms)
        choicePhotoWrapper()


    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        super.onPermissionsDenied(requestCode, perms)
        ToastUtils.show("拒绝权限将无法发表朋友圈")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            if (cb_moment_add_single_choice.isChecked()) {
                snpl_moment_add_photos.setData(BGAPhotoPickerActivity.getSelectedPhotos(data))
            } else {
                snpl_moment_add_photos.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data))
            }
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            snpl_moment_add_photos.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data))
        }

    }
}