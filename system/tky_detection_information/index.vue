<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">报验单编号</label>
        <el-input v-model="query.bydbh" clearable placeholder="报验单编号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">用户账号</label>
        <el-input v-model="query.account" clearable placeholder="用户账号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">试验类型</label>
        <el-input v-model="query.testType" clearable placeholder="试验类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">实际检测开始里程</label>
        <el-input v-model="query.sjstartMile" clearable placeholder="实际检测开始里程" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">实际检测结束里程</label>
        <el-input v-model="query.sjstopMile" clearable placeholder="实际检测结束里程" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">厚度数据</label>
        <el-input v-model="query.appFileTypeRadar" clearable placeholder="厚度数据" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">现场照片</label>
        <el-input v-model="query.appFileTypePhoto" clearable placeholder="现场照片" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">备注1</label>
        <el-input v-model="query.beizhu1" clearable placeholder="备注1" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">检测数据id</label>
        <el-input v-model="query.id" clearable placeholder="检测数据id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="报验单编号" prop="bydbh">
            <el-input v-model="form.bydbh" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户账号">
            <el-input v-model="form.account" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="试验类型">
            <el-input v-model="form.testType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="实际检测开始里程">
            <el-input v-model="form.sjstartMile" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="实际检测结束里程">
            <el-input v-model="form.sjstopMile" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="厚度数据">
            <el-input v-model="form.appFileTypeRadar" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="现场照片">
            <el-input v-model="form.appFileTypePhoto" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注1">
            <el-input v-model="form.beizhu1" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="检测数据id">
            <el-input v-model="form.id" style="width: 370px;" />
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
        <el-table-column prop="bydbh" label="报验单编号" />
        <el-table-column prop="account" label="用户账号" />
        <el-table-column prop="testType" label="试验类型" />
        <el-table-column prop="sjstartMile" label="实际检测开始里程" />
        <el-table-column prop="sjstopMile" label="实际检测结束里程" />
        <el-table-column prop="appFileTypeRadar" label="厚度数据" />
        <el-table-column prop="appFileTypePhoto" label="现场照片" />
        <el-table-column prop="beizhu1" label="备注1" />
        <el-table-column prop="id" label="检测数据id" />
        <el-table-column v-if="checkPer(['admin','tkyDetectionInformation:edit','tkyDetectionInformation:del'])" label="操作" width="150px" align="center">
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
import crudTkyDetectionInformation from '@/api/tkyDetectionInformation'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { bydbh: null, account: null, testType: null, sjstartMile: null, sjstopMile: null, appFileTypeRadar: null, appFileTypePhoto: null, beizhu1: null, beizhu2: null, beizhu3: null, beizhu4: null, beizhu5: null, id: null }
export default {
  name: 'TkyDetectionInformation',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'api/tky_detection_information', url: 'api/tkyDetectionInformation', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTkyDetectionInformation }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tkyDetectionInformation:add'],
        edit: ['admin', 'tkyDetectionInformation:edit'],
        del: ['admin', 'tkyDetectionInformation:del']
      },
      rules: {
        bydbh: [
          { required: true, message: '报验单编号不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'bydbh', display_name: '报验单编号' },
        { key: 'account', display_name: '用户账号' },
        { key: 'testType', display_name: '试验类型' },
        { key: 'sjstartMile', display_name: '实际检测开始里程' },
        { key: 'sjstopMile', display_name: '实际检测结束里程' },
        { key: 'appFileTypeRadar', display_name: '厚度数据' },
        { key: 'appFileTypePhoto', display_name: '现场照片' },
        { key: 'beizhu1', display_name: '备注1' },
        { key: 'id', display_name: '检测数据id' }
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
