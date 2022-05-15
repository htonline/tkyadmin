<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">ID</label>
        <el-input v-model="query.deviceId" clearable placeholder="ID" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">设备类型</label>
        <el-input v-model="query.deviceType" clearable placeholder="设备类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">设备型号</label>
        <el-input v-model="query.deviceModel" clearable placeholder="设备型号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">设备编号</label>
        <el-input v-model="query.deviceBianhao" clearable placeholder="设备编号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">设备照片</label>
        <el-input v-model="query.devicePhotos" clearable placeholder="设备照片" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">设备证书</label>
        <el-input v-model="query.deviceCertificate" clearable placeholder="设备证书" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID">
            <el-input v-model="form.deviceId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="设备类型">
            <el-input v-model="form.deviceType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="设备型号">
            <el-input v-model="form.deviceModel" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="设备编号">
            <el-input v-model="form.deviceBianhao" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="设备照片">
            <el-input v-model="form.devicePhotos" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="设备证书">
            <el-input v-model="form.deviceCertificate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu1">
            <el-input v-model="form.beizhu1" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu2">
            <el-input v-model="form.beizhu2" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu3">
            <el-input v-model="form.beizhu3" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu4">
            <el-input v-model="form.beizhu4" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu5">
            <el-input v-model="form.beizhu5" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu6">
            <el-input v-model="form.beizhu6" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu7">
            <el-input v-model="form.beizhu7" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu8">
            <el-input v-model="form.beizhu8" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu9">
            <el-input v-model="form.beizhu9" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu10">
            <el-input v-model="form.beizhu10" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu11">
            <el-input v-model="form.beizhu11" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu12">
            <el-input v-model="form.beizhu12" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu13">
            <el-input v-model="form.beizhu13" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu14">
            <el-input v-model="form.beizhu14" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="beizhu15">
            <el-input v-model="form.beizhu15" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="deviceId" label="ID" />
        <el-table-column prop="deviceType" label="设备类型" />
        <el-table-column prop="deviceModel" label="设备型号" />
        <el-table-column prop="deviceBianhao" label="设备编号" />
        <el-table-column prop="devicePhotos" label="设备照片" />
        <el-table-column prop="deviceCertificate" label="设备证书" />
        <el-table-column prop="beizhu1" label="beizhu1" />
        <el-table-column prop="beizhu2" label="beizhu2" />
        <el-table-column prop="beizhu3" label="beizhu3" />
        <el-table-column prop="beizhu4" label="beizhu4" />
        <el-table-column prop="beizhu5" label="beizhu5" />
        <el-table-column prop="beizhu6" label="beizhu6" />
        <el-table-column prop="beizhu7" label="beizhu7" />
        <el-table-column prop="beizhu8" label="beizhu8" />
        <el-table-column prop="beizhu9" label="beizhu9" />
        <el-table-column prop="beizhu10" label="beizhu10" />
        <el-table-column prop="beizhu11" label="beizhu11" />
        <el-table-column prop="beizhu12" label="beizhu12" />
        <el-table-column prop="beizhu13" label="beizhu13" />
        <el-table-column prop="beizhu14" label="beizhu14" />
        <el-table-column prop="beizhu15" label="beizhu15" />
        <el-table-column v-if="checkPer(['admin','deviceInformation:edit','deviceInformation:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudDeviceInformation from '@/api/deviceInformation'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { deviceId: null, deviceType: null, deviceModel: null, deviceBianhao: null, devicePhotos: null, deviceCertificate: null, beizhu1: null, beizhu2: null, beizhu3: null, beizhu4: null, beizhu5: null, beizhu6: null, beizhu7: null, beizhu8: null, beizhu9: null, beizhu10: null, beizhu11: null, beizhu12: null, beizhu13: null, beizhu14: null, beizhu15: null }
export default {
  name: 'DeviceInformation',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/device_information', url: 'api/deviceInformation', idField: 'deviceId', sort: 'deviceId,desc', crudMethod: { ...crudDeviceInformation }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'deviceInformation:add'],
        edit: ['admin', 'deviceInformation:edit'],
        del: ['admin', 'deviceInformation:del']
      },
      rules: {
      },
      queryTypeOptions: [
        { key: 'deviceId', display_name: 'ID' },
        { key: 'deviceType', display_name: '设备类型' },
        { key: 'deviceModel', display_name: '设备型号' },
        { key: 'deviceBianhao', display_name: '设备编号' },
        { key: 'devicePhotos', display_name: '设备照片' },
        { key: 'deviceCertificate', display_name: '设备证书' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
